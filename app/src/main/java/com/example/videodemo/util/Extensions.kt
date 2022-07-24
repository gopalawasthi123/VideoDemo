package com.example.videodemo.util

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.videodemo.R



fun com.google.android.material.imageview.ShapeableImageView.loadImage(imageUrl: String){
        Glide.with(this)
            .load(imageUrl)
            .into(this)
}

fun View.setVisibility(isVisible : Boolean){
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}


