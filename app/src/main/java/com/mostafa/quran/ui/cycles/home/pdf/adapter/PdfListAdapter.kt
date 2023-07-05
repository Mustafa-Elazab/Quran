package com.mostafa.quran.ui.cycles.home.pdf.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mostafa.quran.R
import com.mostafa.quran.databinding.PdfItemLayoutBinding
import com.mostafa.quran.domain.model.PDFModel

class PdfListAdapter(private var listener: OnItemClickListener) :
    ListAdapter<PDFModel, PdfListAdapter.ItemViewHolder>(PDFModelDiffCallback()) {


    interface OnItemClickListener {
        fun openPdf(pdfModel: PDFModel)
    }


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

            listener.openPdf(getItem(position))
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