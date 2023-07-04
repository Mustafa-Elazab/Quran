package com.mostafa.quran.ui.cycles.home.hadith.fragment.hadith

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mostafa.quran.R
import com.mostafa.quran.base.BaseFragment
import com.mostafa.quran.databinding.FragmentHadithContentBinding


class HadithContentFragment :
    BaseFragment<FragmentHadithContentBinding>(R.layout.fragment_hadith_content) {


    override val defineBindingVariables: ((FragmentHadithContentBinding) -> Unit)?
        get() = { binding ->
            binding.lifecycleOwner = viewLifecycleOwner
        }

    private var isExplained: Boolean = true
    private val hadithResponseItem by navArgs<HadithContentFragmentArgs>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


       initView()


    }

    private fun initView() {
        binding.apply {
            appBar.pageTitleTv.text = hadithResponseItem.hadithModel.name

            appBar.btnBack.setOnClickListener {

                findNavController().popBackStack()
            }

            if (hadithResponseItem != null) {
                tvHadithContent.text = hadithResponseItem.hadithModel.hadith
                tvHadithDescription.text = hadithResponseItem.hadithModel.description
            }

            btnHadithExplain.setOnClickListener {

                isExplained = !isExplained


                if (isExplained) {

                    binding.apply {
                        tvHadithContent.visibility = View.VISIBLE
                        tvHadithDescription.visibility = View.GONE
                    }

                } else {
                    binding.apply {
                        tvHadithDescription.visibility = View.VISIBLE
                        tvHadithContent.visibility = View.GONE
                    }
                }
            }

        }
    }
}