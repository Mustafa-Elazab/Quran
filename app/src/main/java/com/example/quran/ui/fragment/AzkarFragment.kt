package com.example.quran.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.quran.databinding.FragmentAzkarBinding
import com.example.quran.model.azkar.AzkarResponse
import com.example.quran.ui.adapter.AzkarAdapter
import com.example.quran.utils.getJsonDataFromAsset
import com.google.gson.Gson


class AzkarFragment : Fragment() {

    lateinit var binding: FragmentAzkarBinding
    val gson = Gson()
    private val listAdapter: AzkarAdapter = AzkarAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAzkarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
        getAzkarList()
        initView()
    }

    private fun initView() {
        binding.apply {
            rvAzkarList.apply {
                adapter = listAdapter
            }
        }
    }

    private fun getAzkarList() {
        val jsonFileString = getJsonDataFromAsset(requireContext(), "azkar_db.json")
        val azkarModel = gson.fromJson(jsonFileString, AzkarResponse::class.java)

        listAdapter.submitList(azkarModel)
    }


}