package com.example.videodemo.ui

import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videodemo.data.VideoX
import com.example.videodemo.repo.VideoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val videoRepo: VideoRepo): ViewModel() {

    private var _mutableVideoList = MutableLiveData<List<VideoX>>()

    val videoList : LiveData<List<VideoX>> = _mutableVideoList

    private var _mutableVideoData = MutableLiveData<VideoX?>()

    val videoData : LiveData<VideoX?> = _mutableVideoData

     var _mutableProgress = MutableLiveData<Int>()

    private var _mutableVideoWatchProgress = MutableLiveData<Int>()

    val videoWatchProgress : LiveData<Int> = _mutableVideoWatchProgress

    fun getVideosFromVideoRepo(){
        viewModelScope.launch {
            val resp = videoRepo.getPopularVideos().let {
                _mutableVideoList.postValue(it)
            }
        }
    }

    fun getVideoData(index : Int =0){
        viewModelScope.launch {
            var videoX = videoList.value?.get(index)
            videoX?.let {
                videoRepo.updateData(videoX)
                _mutableVideoData.postValue(videoX)
                _mutableVideoWatchProgress.postValue(videoX.videoProgress)
            }

        }
    }


   suspend fun updateVideoById(id: Int){
      val videItem = videoRepo.getVideoById(id)
       videItem.numTimesVideoWatched = videItem.numTimesVideoWatched.plus(1)
       videoRepo.updateData(videItem)
    }



}