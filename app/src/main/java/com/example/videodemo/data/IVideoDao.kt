package com.example.videodemo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface IVideoDao {
    @Query("Select * from videox order by duration")
    suspend fun getVideos() : List<VideoX>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videoList : List<VideoX>)
}