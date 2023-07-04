package com.mostafa.quran.ui.cycles.home.alsalah.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.mostafa.quran.R
import com.mostafa.quran.base.BaseFragment
import com.mostafa.quran.databinding.FragmentAlsalahBinding
import com.mostafa.quran.domain.model.alsalah.AlsalahResponse
import com.mostafa.quran.ui.cycles.home.alsalah.adapter.AlsalahAdapter
import com.mostafa.quran.utils.getJsonDataFromAsset


class AlsalahFragment : BaseFragment<FragmentAlsalahBinding>(R.layout.fragment_alsalah) {


    private val listAdapter = AlsalahAdapter()
    override val defineBindingVariables: ((FragmentAlsalahBinding) -> Unit)?
        get() = { binding ->
            binding.lifecycleOwner = viewLifecycleOwner
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAlsalahList()
        initView()
    }

    private fun initView() {


        binding.apply {
            appBar.pageTitleTv.text = "الصلاة"
            appBar.btnBack.setOnClickListener {

                findNavController().popBackStack()
            }
            rvAlsalah.apply {
                adapter = listAdapter
            }
        }
    }

    private fun getAlsalahList() {
        val jsonFileString = getJsonDataFromAsset(requireContext(), "alsalah.json")
        val alsalahModel = Gson().fromJson(jsonFileString, AlsalahResponse::class.java)
        listAdapter.submitList(alsalahModel.chapters)
    }

}