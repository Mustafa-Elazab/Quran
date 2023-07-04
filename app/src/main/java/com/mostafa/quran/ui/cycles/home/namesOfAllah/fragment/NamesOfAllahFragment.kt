package com.mostafa.quran.ui.cycles.home.namesOfAllah.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.mostafa.quran.R
import com.mostafa.quran.base.BaseFragment
import com.mostafa.quran.databinding.FragmentNamesOfAllahBinding
import com.mostafa.quran.domain.model.names_allah.NamesOfAllahResponse
import com.mostafa.quran.ui.cycles.home.namesOfAllah.adapter.AllahNamesAdapter
import com.mostafa.quran.utils.getJsonDataFromAsset


class NamesOfAllahFragment :
    BaseFragment<FragmentNamesOfAllahBinding>(R.layout.fragment_names_of_allah) {


    private val allahNamesAdapter = AllahNamesAdapter()

    override val defineBindingVariables: ((FragmentNamesOfAllahBinding) -> Unit)?
        get() = { binding ->
            binding.lifecycleOwner = viewLifecycleOwner
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            appBar.pageTitleTv.text = "اسماء الله الحسني"

            appBar.btnBack.setOnClickListener {

                findNavController().popBackStack()
            }
            rvAllahName.apply {
                adapter = allahNamesAdapter
            }
        }

        getAllahNamesList()

    }


    private fun getAllahNamesList() {
        val jsonFileString = getJsonDataFromAsset(requireContext(), "Names_Of_Allah.json")
        val namesOfAllahResponse = Gson().fromJson(jsonFileString, NamesOfAllahResponse::class.java)

        allahNamesAdapter.submitList(namesOfAllahResponse.chapters)


    }


}