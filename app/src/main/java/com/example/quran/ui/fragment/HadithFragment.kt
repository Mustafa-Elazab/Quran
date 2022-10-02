package com.example.quran.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.quran.databinding.FragmentHadithBinding
import com.example.quran.model.hadith.HadithResponse
import com.example.quran.ui.adapter.HadithAdapter
import com.example.quran.utils.getJsonDataFromAsset
import com.google.gson.Gson


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

        binding.btnBack.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
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