package com.example.quran.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.quran.R
import com.example.quran.databinding.FragmentHomeBinding
import com.example.quran.model.FeatureModel
import com.example.quran.model.PrayTimeModel
import com.example.quran.ui.adapter.FeatureAdapter
import com.example.quran.ui.adapter.PrayTimeAdapter
import com.example.quran.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@AndroidEntryPoint
@ExperimentalCoroutinesApi
class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val featureAdapter = FeatureAdapter()
    private val prayTimeAdapter = PrayTimeAdapter()
    private val homeViewModel: HomeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val featureList = listOf<FeatureModel>(
            FeatureModel(
                id = 1,
                name = "قرأن كريم",
                imageId = R.drawable.ic_quransvg
            ),
            FeatureModel(
                id = 2,
                name = "الاحاديث الاربعين",
                imageId = R.drawable.quran
            ),
            FeatureModel(
                id = 3,
                name = "اذكار و ادعيه",
                imageId = R.drawable.namaste
            ),
            FeatureModel(
                id = 4,
                name = "البوصلة",
                imageId = R.drawable.kaaba
            ),
            FeatureModel(
                id = 5,
                name = "سبحة",
                imageId = R.drawable.tasbih
            ),
            FeatureModel(
                id = 6,
                name = "اسماء الله",
                imageId = R.drawable.allah
            ),
            FeatureModel(
                id = 7,
                name = "الصلاة",
                imageId = R.drawable.praying
            ),
            FeatureModel(
                id = 8,
                name = "اوقات الصلاة",
                imageId = R.drawable.prayertime
            ),
        )


        featureAdapter.submitList(featureList)
        binding.rvHomeLayout.apply {
            adapter = featureAdapter
        }


    }
}