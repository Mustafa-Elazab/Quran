package com.mostafa.quran.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter


@BindingAdapter("load_image")
fun setImageViewResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}

