package com.mostafa.quran.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.mostafa.quran.databinding.FragmentHadithBinding
import com.mostafa.quran.domain.model.hadith.HadithResponse
import com.mostafa.quran.ui.adapter.HadithAdapter
import com.mostafa.quran.utils.getJsonDataFromAsset


class HadithFragment : Fragment() {

    lateinit var binding: FragmentHadithBinding
    val gson = Gson()
    private val listAdapter: HadithAdapter = HadithAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHadithBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.appBar.toolbar.setTitle("احاديث")

        binding.appBar.toolbar.setNavigationOnClickListener {

            findNavController().popBackStack()
        }
        getHadithList()
        initRecycler()
    }

    private fun initRecycler() {
        binding.apply {
            rvHadith.apply {
                adapter = listAdapter
            }
        }
    }

    private fun getHadithList() {
        val jsonFileString = getJsonDataFromAsset(requireContext(), "hadith.json")
        val hadithModel = gson.fromJson(jsonFileString, HadithResponse::class.java)

        listAdapter.submitList(hadithModel)
    }
}