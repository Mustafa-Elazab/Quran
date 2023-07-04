package com.mostafa.quran.ui.cycles.home.azkar.fragment.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.mostafa.quran.R
import com.mostafa.quran.base.BaseFragment
import com.mostafa.quran.databinding.FragmentAzkarBinding
import com.mostafa.quran.domain.model.azkar.AzkarResponse
import com.mostafa.quran.ui.cycles.home.azkar.adapter.AzkarListAdapter
import com.mostafa.quran.ui.cycles.home.azkar.fragment.viewmodel.AzkarViewModel
import com.mostafa.quran.ui.cycles.home.azkar.fragment.viewmodel.ValidationErrors
import com.mostafa.quran.utils.getJsonDataFromAsset
import com.mostafa.quran.utils.onDone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class AzkarFragment : BaseFragment<FragmentAzkarBinding>(R.layout.fragment_azkar) {


    private val viewModel: AzkarViewModel by viewModels()
    override val defineBindingVariables: ((FragmentAzkarBinding) -> Unit)?
        get() = { binding ->
            binding.lifecycleOwner = viewLifecycleOwner
        }


    private val listAdapter = AzkarListAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAzkarList()
        binding.apply {
            rvAzkarList.apply {
                adapter = listAdapter

            }
            appBar.pageTitleTv.text = "اذكار و ادعية"

            appBar.btnBack.setOnClickListener {

                findNavController().popBackStack()
            }
            etSearch.onDone {
                val text = etSearch.text.toString()
                viewModel.validateText(text, getAzkarList())

            }
        }


        collectFlows(listOf(::collectValidateErrors, ::collectSearchState))


    }

    private suspend fun collectSearchState() {
        viewModel.searchResults.collectLatest {
            listAdapter.submitList(it)
        }
    }


    private suspend fun collectValidateErrors() {
        viewModel.validationState.collectLatest {
            when (it) {
                ValidationErrors.TEXT -> binding.etSearch.error = "error"
            }
        }
    }


    private fun getAzkarList(): AzkarResponse {
        val jsonFileString = getJsonDataFromAsset(requireContext(), "azkar_db.json")
        val azkarModel = Gson().fromJson(jsonFileString, AzkarResponse::class.java)
        listAdapter.submitList(azkarModel)
        return azkarModel

    }


}