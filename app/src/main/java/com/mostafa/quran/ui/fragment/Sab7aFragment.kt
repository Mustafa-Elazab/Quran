package com.mostafa.quran.ui.fragment

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mostafa.quran.databinding.FragmentSab7aBinding


class Sab7aFragment : Fragment() {

    lateinit var binding: FragmentSab7aBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSab7aBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val spinner = binding.categoriesSpinner


        binding.appBar.toolbar.setTitle("السبحه")

        binding.appBar.toolbar.setNavigationOnClickListener {

            findNavController().popBackStack()
        }
        val categories =
            mutableListOf("الله اكبر", "لا اله الا الله", "سبحان الله", "استغفر الله", "الحمد لله")

        categories.add(0, "اختار التسبيح")

        val adapter: ArrayAdapter<String> = object : ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            categories
        ) {
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView = super.getDropDownView(
                    position,
                    convertView,
                    parent
                ) as TextView
                // set item text bold
                view.setTypeface(view.typeface, Typeface.BOLD)

                // set item padding
                view.setPadding(64, 0, 128, 0)

                // set selected item style
                if (position == spinner.selectedItemPosition
                    && position != 0
                ) {
                    view.background = ColorDrawable(
                        Color.parseColor("#F8F8F8")
                    )
                    view.setTextColor(
                        Color.parseColor("#0018A8")
                    )
                }

                // make hint item color gray
                if (position == 0) {
                    view.setTextColor(Color.LTGRAY)
                }

                return view
            }

            override fun isEnabled(position: Int): Boolean {
                // disable first item
                // first item is display as hint
                return position != 0
            }


        }
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object
            : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // by default spinner initial selected item is first item
                var count = 1

                if (position != 0) {
                    binding.btnCount.setOnClickListener {

                        binding.tvCount.text = count++.toString()

                    }

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // another interface callback
                Toast.makeText(requireContext(), "اختار نوع التسبيح", Toast.LENGTH_SHORT).show()
            }
        }
    }


}

