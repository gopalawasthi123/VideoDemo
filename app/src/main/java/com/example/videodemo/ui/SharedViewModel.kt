package com.example.videodemo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videodemo.data.VideoX
import com.example.videodemo.repo.VideoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val videoRepo: VideoRepo): ViewModel() {

    private var _mutableVideoList = MutableLiveData<List<VideoX>>()

    val videoList : LiveData<List<VideoX>> = _mutableVideoList

    private var _mutableVideoData = MutableLiveData<String?>()

    val videoData : LiveData<String?> = _mutableVideoData

     var _mutableProgress = MutableLiveData<Int>()

    //val videoProgress : LiveData<Int> = _mutableProgress


    fun getVideosFromVideoRepo(){
        viewModelScope.launch {
            val resp = videoRepo.getPopularVideos().let {
                _mutableVideoList.postValue(it)
            }
        }
    }

    fun getVideoData(index : Int =0){
        viewModelScope.launch {
            var item = videoList.value?.get(index)
            _mutableVideoData.postValue(item?.video_files!![2].link)
        }


    }

}