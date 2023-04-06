package com.mostafa.quran.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mostafa.quran.databinding.FragmentAzkarContentBinding
import com.mostafa.quran.ui.adapter.AzkarContentAdapter


class AzkarContentFragment : Fragment(), AzkarContentAdapter.OnItemClickListener {

    lateinit var binding: FragmentAzkarContentBinding
    private val azakrResponseItem by navArgs<AzkarContentFragmentArgs>()
    private val listAdapter: AzkarContentAdapter = AzkarContentAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAzkarContentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.appBar.toolbar.setTitle(azakrResponseItem.azkarModel.category)

        binding.appBar.toolbar.setNavigationOnClickListener {

            findNavController().popBackStack()
        }


        listAdapter.submitList(azakrResponseItem.azkarModel.array)

        initRecycler()

    }

    private fun initRecycler() {
        binding.rvAzkarContentItem.apply {
            adapter = listAdapter
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