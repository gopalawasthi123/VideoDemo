package com.example.videodemo.util

import androidx.room.TypeConverter
import com.example.videodemo.data.VideoFile
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataTypeConverter {

    @TypeConverter
    fun toVideoFile(json: String) : List<VideoFile>{
        val type = object : TypeToken<List<VideoFile>>(){}.type
        return Gson().fromJson(json,type)
    }

    @TypeConverter
    fun toJsonString(list : List<VideoFile>): String{
        val type = object :TypeToken<List<VideoFile>>(){}.type
        return Gson().toJson(list,type)
    }
}