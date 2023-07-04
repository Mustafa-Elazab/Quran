package com.mostafa.quran.ui.cycles.home.hadith.fragment.list

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.mostafa.quran.R
import com.mostafa.quran.base.BaseFragment
import com.mostafa.quran.databinding.FragmentHadithBinding
import com.mostafa.quran.domain.model.hadith.HadithResponse
import com.mostafa.quran.ui.cycles.home.hadith.adapter.HadithAdapter
import com.mostafa.quran.utils.getJsonDataFromAsset


class HadithFragment : BaseFragment<FragmentHadithBinding>(R.layout.fragment_hadith) {

    override val defineBindingVariables: ((FragmentHadithBinding) -> Unit)?
        get() = { binding ->
            binding.lifecycleOwner = viewLifecycleOwner
        }

    private val listAdapter = HadithAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.apply {
            appBar.pageTitleTv.text = "الاحاديث"

            appBar.btnBack.setOnClickListener {

                findNavController().popBackStack()
            }
            rvHadith.apply {
                adapter = listAdapter
            }
        }

        getHadithList()
    }


    private fun getHadithList() {
        val jsonFileString = getJsonDataFromAsset(requireContext(), "hadith.json")
        val hadithModel = Gson().fromJson(jsonFileString, HadithResponse::class.java)

        listAdapter.submitList(hadithModel)
    }
}