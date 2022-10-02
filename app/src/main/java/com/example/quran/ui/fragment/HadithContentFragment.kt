package com.example.quran.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.quran.databinding.FragmentHadithContentBinding


class HadithContentFragment : Fragment() {

    lateinit var binding: FragmentHadithContentBinding
    private var isExplained: Boolean = true
    private val hadithResponseItem by navArgs<HadithContentFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHadithContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
        binding.apply {

            tvHadithName.text = hadithResponseItem.hadithModel.name
            tvHadithContent.text = hadithResponseItem.hadithModel.hadith
            tvHadithDescription.text = hadithResponseItem.hadithModel.description
        }

        binding.btnHadithExplain.setOnClickListener {

            isExplained =! isExplained
            Log.i("TAG", "onViewCreated: $isExplained")

            if (isExplained) {

                binding.apply {
                    card.visibility = View.VISIBLE
                    cardExplain.visibility = View.GONE
                }

            } else {
                binding.apply {
                    cardExplain.visibility = View.VISIBLE
                    card.visibility = View.GONE
                }
            }
        }

    }
}