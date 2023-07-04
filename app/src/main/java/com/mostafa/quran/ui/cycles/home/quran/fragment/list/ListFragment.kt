package com.mostafa.quran.ui.cycles.home.quran.fragment.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.mostafa.quran.R
import com.mostafa.quran.base.BaseFragment
import com.mostafa.quran.databinding.FragmentListBinding
import com.mostafa.quran.domain.model.ChapterX
import com.mostafa.quran.domain.model.QuranResponse
import com.mostafa.quran.ui.cycles.home.quran.adapter.QuranAdapter
import com.mostafa.quran.ui.cycles.home.quran.viewmodel.QuranViewModel
import com.mostafa.quran.ui.cycles.home.quran.viewmodel.ValidationErrors
import com.mostafa.quran.utils.getJsonDataFromAsset
import com.mostafa.quran.utils.onDone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding>(R.layout.fragment_list) {

    private val viewModel: QuranViewModel by viewModels()
    override val defineBindingVariables: ((FragmentListBinding) -> Unit)?
        get() = { binding ->
            binding.lifecycleOwner = viewLifecycleOwner
        }


    private val listAdapter = QuranAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getQuranList()

        binding.apply {
            appBar.pageTitleTv.text = "القران الكريم"
            appBar.btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            rvQuranList.apply {
                adapter = listAdapter
            }
            etSearch.onDone {
                val text = etSearch.text.toString()
                viewModel.validateText(text, getQuranList())

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


    private fun getQuranList(): List<ChapterX> {
        val jsonFileString = getJsonDataFromAsset(requireContext(), "Quran.json")
        val quranModel = Gson().fromJson(jsonFileString, QuranResponse::class.java)
        listAdapter.submitList(quranModel.chapters)
        return quranModel.chapters

    }

}