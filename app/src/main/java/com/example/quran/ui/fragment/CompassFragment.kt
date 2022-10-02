package com.example.quran.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.quran.databinding.FragmentCompassBinding
import com.example.quran.utils.QiblaCompassView
import com.example.quran.utils.QiblaDegreeListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@AndroidEntryPoint
@ExperimentalCoroutinesApi
class CompassFragment : Fragment() {


    private lateinit var binding: FragmentCompassBinding
    private lateinit var qiblaCompassView: QiblaCompassView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompassBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
        qiblaCompassView = binding.qiblaCompassView
        qiblaCompassView.degreeListener = object : QiblaDegreeListener {
            override fun onDegreeChange(degree: Float) {

            }

        }
    }


}




