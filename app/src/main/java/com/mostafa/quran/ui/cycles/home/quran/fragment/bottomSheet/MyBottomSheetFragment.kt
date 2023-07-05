package com.mostafa.quran.ui.cycles.home.quran.fragment.bottomSheet

import android.R
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mostafa.quran.databinding.FragmentBottomSheetBinding

class MyBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetBinding? = null
    private var binding = _binding

    private lateinit var fontList: List<Typeface>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding?.root!!

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        fontList = FontUtils.getAllFonts(requireContext())
//        val fontNames = fontList.map { getFontName(it) } // Retrieve font names from the Typeface objects
//
//        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, fontNames)
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        binding?.fontsSpinner?.adapter = adapter

        binding?.fontsSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedFont = fontList[position]
                // Apply the selected font to your desired text view
                // ...
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle the case when nothing is selected
            }
        }

    }

    private fun getFontName(typeface: Typeface): String {
        val fontFileName = typeface.toString()
        val fontName = fontFileName.substring(fontFileName.lastIndexOf('/') + 1, fontFileName.lastIndexOf('.'))
        return fontName
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}