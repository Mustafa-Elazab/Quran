package com.mostafa.quran.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.mostafa.quran.R
import com.mostafa.quran.databinding.FragmentNamesOfAllahBinding
import com.mostafa.quran.domain.model.names_allah.NamesOfAllahResponse
import com.mostafa.quran.ui.adapter.AllahNamesAdapter
import com.mostafa.quran.utils.getJsonDataFromAsset


class NamesOfAllahFragment : Fragment(), AllahNamesAdapter.OnItemClickListener {

    private lateinit var binding: FragmentNamesOfAllahBinding
    private val allahNamesAdapter: AllahNamesAdapter = AllahNamesAdapter(this)
    private lateinit var namesOfAllahResponse: NamesOfAllahResponse

    private val gson = Gson()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNamesOfAllahBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appBar.toolbar.setTitle("اسماء الله الحسني")

        binding.appBar.toolbar.setNavigationOnClickListener {

            findNavController().popBackStack()
        }
        getAllahNamesList()
        initView()
    }

    private fun initView() {
        binding.apply {
            rvAllahName.apply {
                adapter = allahNamesAdapter
            }
        }
    }

    private fun getAllahNamesList() {
        val jsonFileString = getJsonDataFromAsset(requireContext(), "Names_Of_Allah.json")
        namesOfAllahResponse = gson.fromJson(jsonFileString, NamesOfAllahResponse::class.java)

        allahNamesAdapter.submitList(namesOfAllahResponse.chapters)


    }


    override fun alertDialog(position: Int) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(requireContext())
// set the custom layout
        val view = layoutInflater.inflate(R.layout.custom_dialog, null)
// Get Views references from your layout
        val tvTitle: TextView = view.findViewById(R.id.tv_title)
        val tvDetail: TextView = view.findViewById(R.id.tv_detail)
        val btDone: Button = view.findViewById(R.id.bt_done)

        tvTitle.setText(namesOfAllahResponse.chapters[position].name)
        tvDetail.setText(namesOfAllahResponse.chapters[position].content)

        btDone.setOnClickListener(View.OnClickListener {
            dialog?.dismiss()
        })

        builder.setView(view)

        dialog = builder.create()
        dialog.show()
    }

}