package com.example.videodemo.data

import androidx.room.*
import com.example.videodemo.util.DataTypeConverter
import kotlinx.coroutines.flow.MutableSharedFlow
import java.io.Serializable

@Entity
data class VideoX(
    val duration: Int?,
    val height: Int?,
    @PrimaryKey
    val id: Int?,
    val image: String?,
    val url: String?,
    val width: Int?,
    @TypeConverters(DataTypeConverter::class)
    val video_files: List<VideoFile>?,
    val watchDuration : Int?,


){
    @Ignore
     var videoProgress : Int = 0
}