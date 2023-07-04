package com.mostafa.quran.ui.cycles.home.sab7a.fragment

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mostafa.quran.R
import com.mostafa.quran.base.BaseFragment
import com.mostafa.quran.databinding.FragmentSab7aBinding
import com.mostafa.quran.ui.cycles.home.sab7a.viewmodel.Sab7aViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class Sab7aFragment : BaseFragment<FragmentSab7aBinding>(R.layout.fragment_sab7a) {


    private val viewModel: Sab7aViewModel by viewModels()
    override val defineBindingVariables: ((FragmentSab7aBinding) -> Unit)?
        get() = { binding ->
            binding.lifecycleOwner = viewLifecycleOwner
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val spinner = binding.categoriesSpinner

        binding.apply {
            appBar.pageTitleTv.text = "السبحه"
            appBar.pageTitleTv.setTextColor(resources.getColor(R.color.white))
            appBar.btnBack.setOnClickListener {

                findNavController().popBackStack()
            }
            resetBtn.setOnClickListener {
                viewModel.resetCounter()
            }
            vibrateBtn.setOnClickListener {
                viewModel.toggleVibrationStatus()

            }




        }
        collectFlows(listOf(::collectCounterState, ::collectVibrateState))
        val categories =
            mutableListOf("الله اكبر", "سبحان الله", "الحمد لله")

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


                binding.btnCount.setOnClickListener {
                    viewModel.incrementCounter()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }


    private suspend fun collectCounterState() {
        viewModel.counter.collectLatest {
            binding.tvCount.text = it.toString()
        }
    }


    private suspend fun collectVibrateState() {
        viewModel.vibratorStatus.collectLatest { shouldVibrate ->
            if (shouldVibrate) {
                binding.vibrateBtn.setImageDrawable(resources.getDrawable(R.drawable.baseline_vibration_24))
            } else {
                binding.vibrateBtn.setImageDrawable(resources.getDrawable(R.drawable.baseline_smartphone_24))
            }
            binding.btnCount.setOnClickListener {
                viewModel.vibrateDevice()
                viewModel.incrementCounter()
            }
        }
    }

}

