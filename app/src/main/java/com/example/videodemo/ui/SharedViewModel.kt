package com.example.videodemo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videodemo.data.VideoX
import com.example.videodemo.repo.VideoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val videoRepo: VideoRepo): ViewModel() {

    private var _mutableVideoList = MutableLiveData<List<VideoX>>()

    val videoList : LiveData<List<VideoX>> = _mutableVideoList

    private var _mutableVideoData = MutableLiveData<String?>()

    val videoData : LiveData<String?> = _mutableVideoData

     var _mutableProgress = MutableLiveData<Int>()

    private var _currentVideoPlayingId = MutableLiveData<Int>()

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
            _currentVideoPlayingId.postValue(videoX?.id!!)
            videoX?.numTimesVideoWatched = videoX?.numTimesVideoWatched?.plus(1)!!
            videoRepo.updateData(videoX!!)
            _mutableVideoData.postValue(videoX?.video_files!![2].link)
            _mutableVideoWatchProgress.postValue(videoX.videoProgress)
        }
    }



     fun updateVideoItem(watchPercentage : Int){
        viewModelScope.launch {
           var videoX : VideoX?= _currentVideoPlayingId.value?.let { videoRepo.getVideoById(it) }
            videoX?.videoProgress = watchPercentage
            videoRepo.updateData(videoX!!)
        }
    }

}