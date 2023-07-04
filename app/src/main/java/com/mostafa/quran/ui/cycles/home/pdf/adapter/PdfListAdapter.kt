package com.mostafa.quran.ui.cycles.home.pdf.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mostafa.quran.R
import com.mostafa.quran.databinding.PdfItemLayoutBinding
import com.mostafa.quran.domain.model.PDFModel
import com.mostafa.quran.ui.cycles.home.pdf.PdfListFragmentDirections

class PdfListAdapter :
    ListAdapter<PDFModel, PdfListAdapter.ItemViewHolder>(PDFModelDiffCallback()) {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.pdf_item_layout,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.view.model = item

        holder.itemView.setOnClickListener {

            val action =
                PdfListFragmentDirections.actionPdfListFragmentToPdfViewerFragment(getItem(position).filePath)
           holder.itemView.findNavController().navigate(action)


        }


    }

    inner class ItemViewHolder(val view: PdfItemLayoutBinding) :
        RecyclerView.ViewHolder(view.root) {

    }

    class PDFModelDiffCallback : DiffUtil.ItemCallback<PDFModel>() {
        override fun areItemsTheSame(oldItem: PDFModel, newItem: PDFModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PDFModel, newItem: PDFModel): Boolean {
            return oldItem == newItem
        }
    }


}