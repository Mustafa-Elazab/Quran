package com.mostafa.quran.ui.cycles.home.compass.fragment


import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.mostafa.quran.R
import com.mostafa.quran.base.BaseFragment
import com.mostafa.quran.databinding.FragmentCompassBinding
import com.mostafa.quran.utils.QiblaCompassView
import dagger.hilt.android.AndroidEntryPoint



class CompassFragment : BaseFragment<FragmentCompassBinding>(R.layout.fragment_compass) {


    private lateinit var qiblaCompassView: QiblaCompassView


    override val defineBindingVariables: ((FragmentCompassBinding) -> Unit)?
        get() = { binding ->
            binding.lifecycleOwner = viewLifecycleOwner
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appBar.pageTitleTv.text = "البوصله"

        binding.appBar.btnBack.setOnClickListener {

            findNavController().popBackStack()
        }


        qiblaCompassView = binding.qiblaCompassView

    }


}




