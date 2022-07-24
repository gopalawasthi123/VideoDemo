package com.example.videodemo.util
import android.view.View
import com.bumptech.glide.Glide

fun com.google.android.material.imageview.ShapeableImageView.loadImage(imageUrl: String){
        Glide.with(this)
            .load(imageUrl)
            .into(this)
}

fun View.setVisibility(isVisible : Boolean){
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}


