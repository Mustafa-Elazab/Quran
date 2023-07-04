package com.mostafa.quran.ui.cycles.home.alsalah.fragment.list

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mostafa.quran.R
import com.mostafa.quran.base.BaseFragment
import com.mostafa.quran.databinding.FragmentAlsalahContentBinding
import com.mostafa.quran.ui.cycles.home.hadith.fragment.hadith.HadithContentFragmentArgs

class AlsalahContentFragment :
    BaseFragment<FragmentAlsalahContentBinding>(R.layout.fragment_alsalah_content) {


    private val model by navArgs<AlsalahContentFragmentArgs>()

    override val defineBindingVariables: ((FragmentAlsalahContentBinding) -> Unit)?
        get() = { binding ->
            binding.lifecycleOwner = viewLifecycleOwner
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            appBar.btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            if(model != null){
                appBar.pageTitleTv.text = model.model.name.toString()
                tvAlsalahContent.text = model.model.content.toString()
            }
        }

    }

}