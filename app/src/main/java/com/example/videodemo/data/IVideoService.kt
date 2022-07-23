package com.example.videodemo.data

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IVideoService {

    @GET("/videos/popular")
   suspend fun getPopularVideos(@Query("per_page") itemsCount : Int) :Response<VideoRoot>

}