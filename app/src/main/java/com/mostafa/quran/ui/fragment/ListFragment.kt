package com.mostafa.quran.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.mostafa.quran.databinding.FragmentListBinding
import com.mostafa.quran.domain.model.QuranResponse
import com.mostafa.quran.ui.adapter.QuranAdapter
import com.mostafa.quran.utils.getJsonDataFromAsset


class ListFragment : Fragment() {

    lateinit var binding: FragmentListBinding
    val gson = Gson()

    private val listAdapter: QuranAdapter = QuranAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appBar.toolbar.setTitle("القران الكريم")

        binding.appBar.toolbar.setNavigationOnClickListener {

            findNavController().popBackStack()
        }
        getQuranList()
        initView()
    }

    private fun initView() {
        binding.apply {
            rvQuranList.apply {
                adapter = listAdapter
            }
        }
    }

    private fun getQuranList() {
        val jsonFileString = getJsonDataFromAsset(requireContext(), "Quran.json")
        val quranModel = gson.fromJson(jsonFileString, QuranResponse::class.java)

        listAdapter.submitList(quranModel.chapters)

        Log.i("data", quranModel.chapters[0].content)


    }

}