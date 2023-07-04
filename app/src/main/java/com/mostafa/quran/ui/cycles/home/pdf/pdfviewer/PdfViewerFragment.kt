package com.mostafa.quran.ui.cycles.home.pdf.pdfviewer

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mostafa.quran.R
import com.mostafa.quran.base.BaseFragment
import com.mostafa.quran.databinding.FragmentPdfViewerBinding
import java.io.File

class PdfViewerFragment : BaseFragment<FragmentPdfViewerBinding>(R.layout.fragment_pdf_viewer) {


    val navArgs by navArgs<PdfViewerFragmentArgs>()

    override val defineBindingVariables: ((FragmentPdfViewerBinding) -> Unit)?
        get() = { binding ->
            binding.lifecycleOwner = viewLifecycleOwner
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            appBar.btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        if(!navArgs.filePath.isEmpty()){
            displayPDF(navArgs.filePath)
        }else{
            Toast.makeText(requireContext(),"",Toast.LENGTH_SHORT).show()
        }


    }


    private fun displayPDF(filePath: String) {

        binding.pdfViewer.fromAsset(filePath)
            .enableSwipe(true)
            .enableDoubletap(true)
            .swipeVertical(false)
            .defaultPage(1)
            .showPageWithAnimation(true)
            .showMinimap(false)
            .enableAnnotationRendering(false)
            .password(null)
            .onError { t ->
                Toast.makeText(requireContext(), "Error loading PDF: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
            .load()

    }

}
