package com.mostafa.quran.ui.cycles.home.pdf.pdfviewer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mostafa.quran.R
import com.mostafa.quran.base.BaseFragment
import com.mostafa.quran.databinding.FragmentPdfViewerBinding
import com.rajat.pdfviewer.PdfViewerActivity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

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

        if (!navArgs.filePath.isEmpty()) {
            val pdfUri = navArgs.filePath
            Log.d("TAG", "onViewCreated: ${navArgs.filePath}")


        } else {
            Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show()
        }


    }


//    private fun displayPDF(filePath: String) {
//
//        binding.pdfViewer.fromAsset(filePath)
//            .enableSwipe(true)
//            .enableDoubletap(true)
//            .defaultPage(1)
//            .load()
//
//    }


    fun displayPdf(context: Context, pdfPath: String, imageView: AppCompatImageView) {
        try {
            val assetManager = context.assets
            val inputStream = assetManager.open(pdfPath)

            val outputFile = File(context.cacheDir, "temp.pdf")
            val outputStream = FileOutputStream(outputFile)

            // Copy the PDF file from assets to the temporary file
            inputStream.copyTo(outputStream)
            inputStream.close()
            outputStream.close()

            // Obtain an instance of PdfRenderer
            val fileDescriptor =
                ParcelFileDescriptor.open(outputFile, ParcelFileDescriptor.MODE_READ_ONLY)
            val pdfRenderer = PdfRenderer(fileDescriptor)

            // Open the PDF document (assuming the first page)
            val pageIndex = 0
            val pdfPage = pdfRenderer.openPage(pageIndex)

            // Render the PDF page
            val bitmap = Bitmap.createBitmap(pdfPage.width, pdfPage.height, Bitmap.Config.ARGB_8888)
            pdfPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

            // Display the rendered page in the ImageView
            imageView.setImageBitmap(bitmap)

            // Clean up resources
            pdfPage.close()
            pdfRenderer.close()
            fileDescriptor.close()
            outputFile.delete()

        } catch (e: IOException) {
            e.printStackTrace()
            // Handle any exceptions
        }
    }


}
