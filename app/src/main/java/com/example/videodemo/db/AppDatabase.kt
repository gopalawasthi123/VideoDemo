package com.example.videodemo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.videodemo.data.IVideoDao
import com.example.videodemo.data.VideoFile
import com.example.videodemo.data.VideoX
import com.example.videodemo.util.DataTypeConverter

@Database(entities = [VideoX ::class],version = 1)
@TypeConverters(DataTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun VideoDao() : IVideoDao
}