package com.example.videodemo.data

import androidx.room.*


@Dao
interface IVideoDao {
    @Query("Select * from videox order by numTimesVideoWatched desc, duration desc")
    suspend fun getVideos() : List<VideoX>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videoList : List<VideoX>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateVideo(videoX: VideoX)

    @Query("Select * from videox where id = :id")
    suspend fun getVideoById(id:Int) : VideoX

}