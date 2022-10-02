package com.example.quran.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.example.quran.databinding.FragmentPrayTimeBinding
import com.example.quran.model.PrayTimeModel
import com.example.quran.ui.adapter.PrayTimeAdapter
import com.example.quran.ui.viewmodel.HomeViewModel
import com.example.quran.utils.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PrayTimeFragment : Fragment() {

    lateinit var binding: FragmentPrayTimeBinding
    private val prayTimeAdapter = PrayTimeAdapter()
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPrayTimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCurrentDate()
        observePrayTime()
        binding.btnBack.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

    }

    private fun getCurrentDate() {

        val CURRENT_TIME_FORMAT = "EEEE , dd MMM yy , hh : mm aa"
        val dateFormat = SimpleDateFormat(CURRENT_TIME_FORMAT, Locale.ROOT)

        var formatted = dateFormat.format(Calendar.getInstance().time)

//        val current = LocalDateTime.now()
//
//        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
//        val formatted = current.format(formatter)

        //       binding.tvDate.text = formatted
//        Log.i("TAG", "onViewCreated: $formatted")
       val strs = formatted.split(",").toTypedArray()
        binding.tvDate.text = strs[0]
        binding.tvDate2.text = strs[1]
        binding.tvDate3.text = strs[2]
    }


    private fun observePrayTime() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.prayTime.collect { state ->
                    when (state) {
//                        is State.Loading -> Toast.makeText(
//                            activity,
//                            "Loading data",
//                            Toast.LENGTH_SHORT
//                        ).show()
                        is State.Success -> {
                            if (state.data != null) {

                                var timings = state.data.data.timings
                                Log.i("@@@", state.data.toString())

                                val prayTimeList = listOf<PrayTimeModel>(

                                    PrayTimeModel(
                                        id = 1,
                                        name = "الفجر",

                                        time = convertTime(timings.Fajr)
                                    ),
                                    PrayTimeModel(
                                        id = 2,
                                        name = "الضهر",

                                        time = convertTime(timings.Dhuhr)
                                    ),
                                    PrayTimeModel(
                                        id = 3,
                                        name = "العصر",

                                        time = convertTime(timings.Asr)
                                    ),
                                    PrayTimeModel(
                                        id = 4,
                                        name = "المغرب",

                                        time = convertTime(timings.Maghrib)
                                    ),
                                    PrayTimeModel(
                                        id = 5,
                                        name = "العشاء",

                                        time = convertTime(timings.Isha)
                                    ),

                                    )
                                prayTimeAdapter.submitList(prayTimeList)
                                initRecycler()
                                //showLoading(false)
                            }
                        }
                        is State.Error -> {
                            Toast.makeText(activity, state.message, Toast.LENGTH_SHORT).show()
                            //  showLoading(false)
                        }
                    }

                }
            }
        }
    }


    private fun initRecycler() {
        binding.rvPrayTime.apply {
            adapter = prayTimeAdapter
        }
    }


    private fun convertTime(time: String): String {
        val strs = time.split(":").toTypedArray()
        var hh: Int = strs[0].toInt()
        val format: String

        if (hh > 12) {
            hh -= 12
            format = "PM"
        } else if (hh == 0) {
            hh = 12
            format = "AM"
        } else if (hh == 12) {
            hh = 12
            format = "PM"
        } else {
            format = "AM"
        }
        val hour = String.format(hh.toString())
        val minute: String = strs[1]

        return "$hour : $minute $format"


    }


}