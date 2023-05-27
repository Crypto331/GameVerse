package com.mobbelldev.gameverse.core.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mobbelldev.gameverse.core.R

fun ImageView.load(imageSource: String?) {
    val options = RequestOptions()
        .override(width, height)
    Glide.with(context.applicationContext)
        .load(imageSource)
        .placeholder(R.drawable.baseline_broken_image_24)
        .apply(options)
        .into(this)
}