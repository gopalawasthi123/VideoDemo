package com.example.videodemo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.videodemo.data.IVideoDao
import com.example.videodemo.data.VideoX

@Database(entities = [VideoX ::class],version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun VideoDao() : IVideoDao
}