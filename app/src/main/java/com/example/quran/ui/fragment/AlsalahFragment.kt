package com.example.quran.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quran.databinding.FragmentAlsalahBinding
import com.example.quran.model.alsalah.AlsalahResponse
import com.example.quran.ui.adapter.AlsalahAdapter
import com.example.quran.utils.getJsonDataFromAsset
import com.google.gson.Gson


class AlsalahFragment : Fragment() {

    lateinit var binding: FragmentAlsalahBinding
    val gson = Gson()
    private val listAdapter: AlsalahAdapter = AlsalahAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAlsalahBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAlsalahList()
        initView()
    }

    private fun initView() {

        binding.btnBack.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        binding.apply {
            rvAlsalah.apply {
                adapter = listAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun getAlsalahList() {
        val jsonFileString = getJsonDataFromAsset(requireContext(), "alsalah.json")
        val alsalahModel = gson.fromJson(jsonFileString, AlsalahResponse::class.java)


        listAdapter.submitList(alsalahModel.chapters)


        Log.i("TAG", "getAlsalahList: ${alsalahModel.chapters}")
    }

}