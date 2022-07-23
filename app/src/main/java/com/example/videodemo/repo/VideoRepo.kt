package com.example.videodemo.repo

import android.provider.SyncStateContract
import com.example.videodemo.data.IVideoDao
import com.example.videodemo.data.IVideoService
import com.example.videodemo.data.VideoX
import com.example.videodemo.util.Constants
import javax.inject.Inject

class VideoRepo @Inject constructor(private val videoService: IVideoService,private val videoDao: IVideoDao) {

    suspend fun getPopularVideos() : List<VideoX>?{

        val videos =  videoDao.getVideos()

        if(videos.isEmpty()){
            videoService.getPopularVideos(Constants.ITEMS_PER_PAGE).let {
                if(it.isSuccessful){
                    it.body()?.videos?.let { it1 -> videoDao.insertVideos(it1) }
                }
                else
                return null
            }
        }
        return videoDao.getVideos()
    }

}