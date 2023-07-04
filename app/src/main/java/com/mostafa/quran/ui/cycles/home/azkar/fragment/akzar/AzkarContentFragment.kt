package com.mostafa.quran.ui.cycles.home.azkar.fragment.akzar

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mostafa.quran.R
import com.mostafa.quran.base.BaseFragment
import com.mostafa.quran.databinding.FragmentAzkarContentBinding
import com.mostafa.quran.ui.cycles.home.azkar.adapter.AzkarContentAdapter


class AzkarContentFragment :
    BaseFragment<FragmentAzkarContentBinding>(R.layout.fragment_azkar_content),
    AzkarContentAdapter.OnItemClickListener {


    override val defineBindingVariables: ((FragmentAzkarContentBinding) -> Unit)?
        get() = { binding ->
            binding.lifecycleOwner = viewLifecycleOwner
        }
    private val azakrResponseItem by navArgs<AzkarContentFragmentArgs>()
    private val listAdapter = AzkarContentAdapter(this)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            appBar.pageTitleTv.text = azakrResponseItem.azkarModel.category

            appBar.btnBack.setOnClickListener {

                findNavController().popBackStack()
            }
            rvAzkarContentItem.apply {
                adapter = listAdapter
            }

        }


        if (azakrResponseItem.azkarModel.array != null) {
            listAdapter.submitList(azakrResponseItem.azkarModel.array)
        }


    }


    override fun azkarShare(position: Int) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, azakrResponseItem.azkarModel.array[position].text)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

}