package com.mostafa.quran.ui.cycles.home.main.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.mostafa.quran.R
import com.mostafa.quran.base.BaseFragment
import com.mostafa.quran.data.remote.response.NetworkResponse
import com.mostafa.quran.databinding.FragmentMainBinding
import com.mostafa.quran.di.NetworkUtils
import com.mostafa.quran.domain.model.FeatureModel
import com.mostafa.quran.domain.model.PrayerTime
import com.mostafa.quran.ui.activity.TAG
import com.mostafa.quran.ui.cycles.home.main.adapter.FeatureAdapter
import com.mostafa.quran.ui.cycles.home.main.adapter.PrayerAdapter
import com.mostafa.quran.ui.cycles.home.main.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {


    private val featureAdapter = FeatureAdapter()
    private val adapter = PrayerAdapter()
    private val viewModel: HomeViewModel by activityViewModels()


    @Inject
    lateinit var networkUtils: NetworkUtils

    override val defineBindingVariables: ((FragmentMainBinding) -> Unit)?
        get() = { binding ->
            binding.lifecycleOwner = viewLifecycleOwner
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.apply {
            rvSchedule.adapter = adapter
        }

        collectFlows(
            listOf(
                ::collectCurrentDayState,
                ::collectRemaingTimeState,
                ::collectCurrentDateState,
                ::collectNextPrayState,
                ::collectAddressState
            )
        )

        val featureList = listOf<FeatureModel>(
            FeatureModel(
                id = 1,
                name = "قرأن كريم",
                imageId = R.drawable.ic_quransvg,

                ),
            FeatureModel(
                id = 2,
                name = "الاحاديث الاربعين",
                imageId = R.drawable.book_open,

                ),
            FeatureModel(
                id = 3,
                name = "اذكار و ادعيه",
                imageId = R.drawable.hands_pray,

                ),
            FeatureModel(
                id = 4,
                name = "البوصلة",
                imageId = R.drawable.compass,


                ),
            FeatureModel(
                id = 5,
                name = "سبحة",
                imageId = R.drawable.beads,


                ),
            FeatureModel(
                id = 6,
                name = "اسماء الله",
                imageId = R.drawable.allah,


                ),
            FeatureModel(
                id = 7,
                name = "الصلاة",
                imageId = R.drawable.quran_islam_svgrepo_com,
            ),
            FeatureModel(
                id = 8,
                name = "مكتبة",
                imageId = R.drawable.library,
            ),
        )


        featureAdapter.submitList(featureList)
        binding.rvCategory.apply {
            adapter = featureAdapter
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun collectCurrentDayState() {
        viewModel.currentDayStateFlow.collectLatest {
            collectTimingsState(it - 1)
        }

    }

    private suspend fun collectNextPrayState() {

        viewModel.nextPrayerStateFlow.collectLatest {
            binding.apply {

                tvNextPrayTime.text = "الصلاة القادمة : $it"
            }
        }

    }

    private suspend fun collectCurrentDateState() {
        viewModel.currentDateStateFlow.collectLatest {
            binding.tvDate.text = it
        }

    }

    private suspend fun collectRemaingTimeState() {

        viewModel.remainingTime.collectLatest {
            binding.tvNextPray.text = "الوقت المتبقي للصلاة القادمة : $it"
        }

    }


    private suspend fun collectAddressState() {
        binding.tvLocation.text = viewModel.city.toString() ?: ""
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun collectTimingsState(index: Int) {
        lifecycleScope.launch {
            viewModel.prayTime.collectLatest {
                when (it) {
                    is NetworkResponse.ApiError -> {
                        Toast.makeText(requireContext(), it.body.data.toString(), Toast.LENGTH_LONG)
                            .show()


                    }

                    NetworkResponse.Loading -> {

                    }

                    is NetworkResponse.NetworkError -> {

                    }

                    is NetworkResponse.Success -> {

                        val timing = it.body.data?.get(index)!!.timings!!


                        val allPrayerTimesInMillis = listOf<Long>(
                            viewModel.timeStringToMillis(timing.fajr.toString()),
                            viewModel.timeStringToMillis(timing.sunrise.toString()),
                            viewModel.timeStringToMillis(timing.dhuhr.toString()),
                            viewModel.timeStringToMillis(timing.asr.toString()),
                            viewModel.timeStringToMillis(timing.maghrib.toString()),
                            viewModel.timeStringToMillis(timing.isha.toString()),
                        )
                        Log.d(TAG, "collectTimingsState: $allPrayerTimesInMillis")
                        viewModel.getNextPrayer(allPrayerTimesInMillis, requireContext())
                        val prayerTimes = listOf(
                            PrayerTime(
                                1,
                                "الفجْر",
                                viewModel.convertToAmPmFormat(time = timing.fajr.toString()),
                                viewModel.timeStringToMillis(timing.fajr.toString()),
                                R.raw.adzan_fajr
                            ),
                            PrayerTime(
                                1,
                                "الشروق",
                                viewModel.convertToAmPmFormat(time = timing.sunrise.toString()),
                                viewModel.timeStringToMillis(timing.sunrise.toString()),
                                R.raw.adzan_makkah
                            ),
                            PrayerTime(
                                1,
                                "الظُّهْر",
                                viewModel.convertToAmPmFormat(time = timing.dhuhr.toString()),
                                viewModel.timeStringToMillis(timing.dhuhr.toString()),
                                R.raw.adzan_makkah
                            ),
                            PrayerTime(
                                1,
                                "العَصر",
                                viewModel.convertToAmPmFormat(time = timing.asr.toString()),
                                viewModel.timeStringToMillis(timing.asr.toString()),
                                R.raw.adzan_makkah
                            ), PrayerTime(
                                1,
                                "المَغرب",
                                viewModel.convertToAmPmFormat(time = timing.maghrib.toString()),
                                viewModel.timeStringToMillis(timing.maghrib.toString()),
                                R.raw.adzan_makkah
                            ), PrayerTime(
                                1,
                                "العِشاء",
                                viewModel.convertToAmPmFormat(time = timing.isha.toString()),
                                viewModel.timeStringToMillis(timing.isha.toString()),
                                R.raw.adzan_makkah
                            )

                        )
                        adapter.submitList(prayerTimes)
                    }

                    is NetworkResponse.UnknownError -> {
                        Toast.makeText(
                            requireContext(),
                            it.error!!.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()


                    }

                    else -> {

                    }
                }
            }
        }

    }

}