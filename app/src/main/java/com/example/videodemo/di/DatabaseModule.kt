package com.example.videodemo.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.videodemo.data.IVideoDao
import com.example.videodemo.db.AppDatabase
import com.example.videodemo.util.Constants
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDataBase(@ApplicationContext context: Context) : RoomDatabase{
        return Room.databaseBuilder(context,
                    AppDatabase::class.java,
                    Constants.TABLE_NAME).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesVideoDao(appDatabase: AppDatabase) :IVideoDao{
        return appDatabase.VideoDao()
    }

}