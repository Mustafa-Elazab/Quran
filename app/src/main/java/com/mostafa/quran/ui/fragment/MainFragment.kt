package com.mostafa.quran.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mostafa.quran.R
import com.mostafa.quran.base.BaseFragment
import com.mostafa.quran.data.remote.response.NetworkResponse
import com.mostafa.quran.databinding.FragmentMainBinding
import com.mostafa.quran.domain.model.FeatureModel
import com.mostafa.quran.ui.adapter.FeatureAdapter
import com.mostafa.quran.ui.viewmodel.HomeViewModel
import com.mostafa.quran.utils.StaggeredGridItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {


    private val featureAdapter = FeatureAdapter()
    private val viewModel: HomeViewModel by activityViewModels()

    override val defineBindingVariables: ((FragmentMainBinding) -> Unit)?
        get() = { binding ->
            binding.lifecycleOwner = viewLifecycleOwner
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPrayTime()
        binding.rvCategory.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val itemDecoration =
            StaggeredGridItemDecoration(resources.getDimensionPixelSize(R.dimen.margin))
        binding.rvCategory.addItemDecoration(itemDecoration)

        collectFlows(listOf(::observePrayTime))

        val featureList = listOf<FeatureModel>(
            FeatureModel(
                id = 1,
                name = "قرأن كريم",
                imageId = R.drawable.ic_quransvg,
                color = resources.getColor(R.color.purple)
            ),
            FeatureModel(
                id = 2,
                name = "الاحاديث الاربعين",
                imageId = R.drawable.quran_islam_svgrepo_com,
                color = resources.getColor(R.color.light_purple)
            ),
            FeatureModel(
                id = 3,
                name = "اذكار و ادعيه",
                imageId = R.drawable.hands_pray,
                color = resources.getColor(R.color.light_purple)
            ),
            FeatureModel(
                id = 4,
                name = "البوصلة",
                imageId = R.drawable.compass_outline,
                color = resources.getColor(R.color.purple)

            ),
            FeatureModel(
                id = 5,
                name = "سبحة",
                imageId = R.drawable.tasbih,
                color = resources.getColor(R.color.purple)

            ),
            FeatureModel(
                id = 6,
                name = "اسماء الله",
                imageId = R.drawable.allah,
                color = resources.getColor(R.color.light_purple)

            ),
            FeatureModel(
                id = 7,
                name = "الصلاة",
                imageId = R.drawable.mosque_svgrepo_com,
                color = resources.getColor(R.color.light_purple)

            ),
        )







        featureAdapter.submitList(featureList)
        binding.rvCategory.apply {
            adapter = featureAdapter
        }

    }


    private suspend fun observePrayTime() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest {

                when (it) {
                    is NetworkResponse.ApiError -> {

                        Log.d("TAG", "observePrayTime: ${it.body.data}")
                        binding.tvPrayTime.visibility  = View.GONE
                        binding.constraint.visibility = View.GONE
                    }
                    NetworkResponse.Loading -> {
                        binding.tvPrayTime.visibility  = View.GONE
                        binding.constraint.visibility = View.GONE
                    }
                    is NetworkResponse.NetworkError -> {
                        binding.tvPrayTime.visibility  = View.GONE
                        binding.constraint.visibility = View.GONE
                    }
                    is NetworkResponse.Success -> {
                        binding.tvPrayTime.visibility  = View.VISIBLE
                        binding.constraint.visibility = View.VISIBLE
                        var timings = it.body.data?.timings
                        binding.m2tvPrayViewOne.text = convertTime(timings?.fajr!!)
                        binding.m2tvPrayViewTwo.text = convertTime(timings.dhuhr!!)
                        binding.m2tvPrayViewThree.text = convertTime(timings.asr!!)
                        binding.m2tvPrayViewFour.text = convertTime(timings.maghrib!!)
                        binding.m2tvPrayViewFive.text = convertTime(timings.isha!!)
                    }
                    is NetworkResponse.UnknownError -> {
                        binding.tvPrayTime.visibility  = View.GONE
                        binding.constraint.visibility = View.GONE
                    }

                }


            }
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