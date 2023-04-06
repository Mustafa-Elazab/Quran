package com.mostafa.quran.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mostafa.quran.databinding.FragmentQuranContentBinding


class QuranContentFragment : Fragment() {

    private lateinit var binding: FragmentQuranContentBinding

    val model by navArgs<QuranContentFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentQuranContentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.appBar.toolbar.setTitle(model.quranContentModel.name)

        binding.appBar.toolbar.setNavigationOnClickListener {

            findNavController().popBackStack()
        }

        binding.apply {

            tvSurahContent.text = model.quranContentModel.content

        }



    }

}