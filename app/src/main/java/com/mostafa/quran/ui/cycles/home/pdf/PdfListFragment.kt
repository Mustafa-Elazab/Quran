package com.mostafa.quran.ui.cycles.home.pdf

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.mostafa.quran.R
import com.mostafa.quran.base.BaseFragment
import com.mostafa.quran.databinding.FragmentPdfListBinding
import com.mostafa.quran.domain.model.PDFModel
import com.mostafa.quran.ui.cycles.home.pdf.adapter.PdfListAdapter


class PdfListFragment : BaseFragment<FragmentPdfListBinding>(R.layout.fragment_pdf_list) {


    private val pdfAdapter = PdfListAdapter()

    override val defineBindingVariables: ((FragmentPdfListBinding) -> Unit)?
        get() = { binding ->
            binding.lifecycleOwner = viewLifecycleOwner
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            appBar.pageTitleTv.text = "مكتبة"
            appBar.btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

        }

        // Get the list of PDF files from the assets directory
        val pdfFiles = requireContext().assets.list("")?.filter { it.endsWith(".pdf") }

        // Create a list of com.mostafa.quran.domain.model.PDFModel objects
        val pdfModels = pdfFiles?.mapIndexed { index, fileName ->
            PDFModel(index, fileName, fileName)
        } ?: emptyList()



        binding.rvPdfList.adapter = pdfAdapter
        pdfAdapter.submitList(pdfModels)
    }

}