package com.mostafa.quran.ui.cycles.home.main.viewmodel


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafa.quran.data.remote.dto.AladhanResponseDTO
import com.mostafa.quran.data.remote.dto.ErrorResponse
import com.mostafa.quran.data.remote.response.NetworkResponse
import com.mostafa.quran.domain.reminder.AlarmReceiver
import com.mostafa.quran.domain.repository.AlarmRepository
import com.mostafa.quran.domain.usecase.GetPrayTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: GetPrayTimeUseCase,
    private val dataStore: DataStore<Preferences>?,
    private val alarmManager: AlarmManager,
    private val repository: AlarmRepository
) : ViewModel() {

    private val _prayTime =
        MutableStateFlow<NetworkResponse<AladhanResponseDTO, ErrorResponse>?>(null)
    val prayTime get() = _prayTime.asStateFlow()


    private var _locationAddress = MutableStateFlow<String?>("")
    val locationAddress get() = _locationAddress.asStateFlow()


    private val dateFormat = SimpleDateFormat("MMM dd yyyy", Locale.getDefault())
    private val currentDate = Calendar.getInstance()

    private var countdownJob: Job? = null


    private val _remainingTime = MutableStateFlow<String>("")
    val remainingTime get() = _remainingTime.asStateFlow()


    private val _currentDateStateFlow = MutableStateFlow<String>("")
    val currentDateStateFlow get() = _currentDateStateFlow.asStateFlow()

    private val _currentDayStateFlow = MutableStateFlow<Int>(0)
    val currentDayStateFlow get() = _currentDayStateFlow.asStateFlow()


    private val _nextPrayerStateFlow = MutableStateFlow<String>("")
    val nextPrayerStateFlow get() = _nextPrayerStateFlow.asStateFlow()

    private val _locationStateFlow = MutableStateFlow<Location?>(null)
    val locationStateFlow get() = _locationStateFlow.asStateFlow()

    val location: MutableLiveData<Location> = MutableLiveData()

    var city: String? = null
    var latitude: Double? = 0.0
    var longitude: Double? = 0.0

    init {

        updateCurrentDate()
        viewModelScope.launch {
            dataStore?.data?.firstOrNull()?.let { preferences ->
                city = preferences[CITY]
                latitude = preferences[LATITUDE]
                longitude = preferences[LONGITUDE]
            }

        }
    }


    fun fetchPrayTime(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            useCase.invoke(
                latitude = latitude,
                longitude = longitude,
                month = currentDate.get(Calendar.MONTH) + 1,
                year = currentDate.get(Calendar.YEAR)
            )
                .collect {
                    _prayTime.emit(it)
                }
        }
    }


    private fun updateCurrentDate() {
        viewModelScope.launch {
            _currentDateStateFlow.emit(dateFormat.format(currentDate.time))

            _currentDayStateFlow.emit(currentDate.get(Calendar.DAY_OF_MONTH))
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun timeStringToMillis(time: String): Long {
        val timePattern = """\d{2}:\d{2}""".toRegex()
        val matchResult = timePattern.find(time)
        val prayerTime = matchResult?.value
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val localTime = LocalTime.parse(prayerTime, formatter)

        // Add one hour to the given time
        val updatedLocalTime = localTime.minusHours(1)

        val currentDateTime = LocalDateTime.now()
        val timeOfDay = currentDateTime.with(updatedLocalTime)

        // Get the local time zone
        val localTimeZone = ZoneId.systemDefault()

        // Calculate the local time in milliseconds
        val localTimeInMillis = timeOfDay.atZone(localTimeZone).toInstant().toEpochMilli()

        return localTimeInMillis
    }


    fun convertToAmPmFormat(time: String): String {
        val timePattern = """\d{2}:\d{2}""".toRegex()
        val matchResult = timePattern.find(time)
        val prayerTime = matchResult?.value!!
        val parts = prayerTime.split(":")
        val hour = parts[0].toInt()
        val minute = parts[1]
        val amPm = if (hour >= 12) "PM" else "AM"
        val hourInAmPm = if (hour > 12) hour - 12 else hour
        return "$hourInAmPm:$minute $amPm"
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getNextPrayer(prayerTimesInMillis: List<Long>, context: Context) {
        val currentTime =
            LocalDateTime.now().with(LocalTime.now().minusHours(1)).atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli()
        val nextPrayerTime = prayerTimesInMillis.firstOrNull { it > currentTime }
            ?: prayerTimesInMillis.firstOrNull()
        val nextPrayerIndex = prayerTimesInMillis.indexOf(nextPrayerTime)
        val timeDifference = nextPrayerTime?.minus(currentTime) ?: 0L
        val prayerNames = listOf("الفجْر", "الشروق", "الظُّهْر", "العَصر", "المَغرب", "العِشاء")
        val nextPrayer = prayerNames.getOrNull(nextPrayerIndex) ?: "الفجْر"
        _nextPrayerStateFlow.value = nextPrayer

        val list = listOf<Long>(
            prayerTimesInMillis[0].plus(3600000),
            prayerTimesInMillis[2].plus(3600000),
            prayerTimesInMillis[3].plus(3600000),
            prayerTimesInMillis[4].plus(3600000),
            prayerTimesInMillis[5].plus(3600000)

        )

        setAlarms(context = context, list)
        startCountdown(durationMillis = timeDifference)
    }


    private fun startCountdown(durationMillis: Long) {
        countdownJob?.cancel() // Cancel any ongoing countdown job

        countdownJob = viewModelScope.launch {
            var remainingTimeMillis = durationMillis
            while (remainingTimeMillis > 0) {
                _remainingTime.emit(formatTime(remainingTimeMillis))
                delay(1000) // Delay for 1 second
                remainingTimeMillis -= 1000
            }

            _remainingTime.emit("00:00")   // Countdown finished
        }
    }


    private fun formatTime(timeMillis: Long): String {
        val hours = (timeMillis / (1000 * 60 * 60)) % 24
        val minutes = (timeMillis / (1000 * 60)) % 60
        val seconds = (timeMillis / 1000) % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }


    fun getLocationAddress(context: Context, latitude: Double, longitude: Double) {
        viewModelScope.launch {
            Geocoder(context.applicationContext, Locale.getDefault()).apply {
                getFromLocation(latitude, longitude, 1)?.first()
                    ?.let { address ->
                        _locationAddress.emit(buildString {
                            append(address.subAdminArea)
                        })
                        dataStore!!.edit { preferences ->
                            preferences[LATITUDE] = latitude
                            preferences[LONGITUDE] = longitude
                            preferences[CITY] = buildString {
                                append(address.subAdminArea)
                            }
                        }
                    }

            }
        }
    }


    private fun setAlarms(context: Context, alarmTimes: List<Long>) {
        viewModelScope.launch {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val prayerNames = listOf("الفجْر", "الظُّهْر", "العَصر", "المَغرب", "العِشاء")
            repository.setAlarmTimes(alarmTimes)


            for ((index, timeInMillis) in alarmTimes.withIndex()) {
                val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
                    putExtra("PrayName", prayerNames[index])
                }
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    index,
                    alarmIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )


                if (System.currentTimeMillis() < timeInMillis) {

                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        timeInMillis,
                        pendingIntent
                    )
                } else {

                    alarmManager.cancel(pendingIntent)
                }
            }
        }
    }


//    @RequiresApi(Build.VERSION_CODES.O)
//    fun setAlarm(context: Context, timesAlarm: Long,nextPray:String) {
//        val currentTimeInMillis = System.currentTimeMillis()
//        Log.d("TAG", "setAlarm: $currentTimeInMillis")
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val alarmTimeInMillis = currentTimeInMillis + timesAlarm
//
//        val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
//            putExtra("PrayName",nextPray)
//        }
//        val pendingIntent = PendingIntent.getBroadcast(
//            context,
//            0,
//            alarmIntent,
//            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
//        )
//
//        alarmManager.setExactAndAllowWhileIdle(
//            AlarmManager.RTC_WAKEUP,
//            alarmTimeInMillis,
//            pendingIntent
//        )
//
//
//    }

    fun cancelAlarm(context: Context) {
        val alarmIntent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            alarmIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Cancel the alarm
        alarmManager.cancel(pendingIntent)
    }


    companion object {
        val LATITUDE = doublePreferencesKey("latitude")
        val LONGITUDE = doublePreferencesKey("longitude")
        val CITY = stringPreferencesKey("city")

    }

}







