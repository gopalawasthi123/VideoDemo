package com.example.videodemo.repo

import android.provider.SyncStateContract
import com.example.videodemo.data.IVideoDao
import com.example.videodemo.data.IVideoService
import com.example.videodemo.data.VideoX
import com.example.videodemo.util.Constants
import javax.inject.Inject

class VideoRepo @Inject constructor(private val videoService: IVideoService) {

    suspend fun getPopularVideos() : List<VideoX>?{
        videoService.getPopularVideos(Constants.ITEMS_PER_PAGE).let {
            if(it.isSuccessful){
                return it.body()?.videos
            }
            return null
        }

    }

}