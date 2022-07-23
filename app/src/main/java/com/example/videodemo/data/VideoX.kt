package com.example.videodemo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VideoX(
    val duration: Int?,
    val height: Int?,
    @PrimaryKey
    val id: Int?,
    val image: String?,
    val url: String?,
    val width: Int?,
    val watchDuration : Int?,
)