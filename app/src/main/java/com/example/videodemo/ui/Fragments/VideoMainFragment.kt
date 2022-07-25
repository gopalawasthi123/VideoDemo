package com.example.videodemo.ui.Fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import androidx.annotation.FloatRange
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import com.example.videodemo.databinding.FragmentVideoMainBinding
import com.example.videodemo.ui.SharedViewModel
import com.example.videodemo.util.setVisibility
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.analytics.AnalyticsListener
import com.google.android.exoplayer2.analytics.PlaybackStats
import com.google.android.exoplayer2.analytics.PlaybackStatsListener
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlin.math.absoluteValue


/**
 * A simple [Fragment] subclass.
 * Use the [VideoMainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class VideoMainFragment : Fragment() {
    private val sharedViewModel : SharedViewModel by activityViewModels()
    private lateinit var simpleExoplayer: SimpleExoPlayer
    private var playTime = 0L // in ms
    private var pauseTime = 0L // in ms
    private var totalTime = 0L // in ms
    private var pressedPaused = 0
    private var playbackPosition : Int = 0
    private var fragmentVideoMainBinding : FragmentVideoMainBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        // Inflate the layout for this fragment
       val binding = FragmentVideoMainBinding.inflate(layoutInflater,container,false)
        fragmentVideoMainBinding = binding

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        initializePlayer(this)

        simpleExoplayer!!.addListener(object :Player.Listener{
            override fun onPlaybackStateChanged(playbackState: Int) {
                if(playbackState == Player.STATE_IDLE){

                }
                else if(playbackState == Player.STATE_BUFFERING){
                    fragmentVideoMainBinding?.progressBar?.setVisibility(true)
                }
                else if(playbackState == Player.STATE_ENDED){
                    fragmentVideoMainBinding?.progressBar?.setVisibility(false)
                    fragmentVideoMainBinding?.videoWatched?.text = (playTime/1000).toString()
                }
                else if(playbackState == Player.STATE_READY){
                    fragmentVideoMainBinding?.progressBar?.setVisibility(false)
                   lifecycleScope.launchWhenStarted {
                       getSeekBarPosition().observe(viewLifecycleOwner){
                           sharedViewModel._mutableProgress.value = it

                       }
                   }
                }
            }


            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                super.onMediaItemTransition(mediaItem, reason)
            }

        })

       simpleExoplayer.addAnalyticsListener(PlaybackStatsListener(false,object :PlaybackStatsListener.Callback{
           override fun onPlaybackStatsReady(
               eventTime: AnalyticsListener.EventTime,
               playbackStats: PlaybackStats,
           ) {
               var watchPercentage : Int = (playbackStats.totalPlayTimeMs /1000).toInt()
               sharedViewModel.updateVideoItem(watchPercentage)
           }

       }))

    }


    private fun getSeekBarPosition() = liveData<Int> {
        while (true){
            val minDuration = 0.0f
            val currentpos: Int = (simpleExoplayer.currentPosition/1000).toInt()
            val maxDuration :Int = (simpleExoplayer.duration/1000).toInt()
            var durationPlayed = ((currentpos - minDuration)*100) /(maxDuration - minDuration)
            emit(durationPlayed.toInt())
            delay(1000L)
            if(currentpos == maxDuration)
                break
        }
    }


    private fun initializePlayer(videoMainFragment: VideoMainFragment) {
        simpleExoplayer = SimpleExoPlayer.Builder(videoMainFragment.requireContext()).build()
        sharedViewModel.videoData.observe(viewLifecycleOwner){
            if(simpleExoplayer.isPlaying){
             //   fragmentVideoMainBinding?.videoWatched?.text = calculatePercentageVideoWatched(simpleExoplayer.currentPosition,0).toString()
            }
            preparePlayer(it)
        }
        sharedViewModel.videoWatchProgress.observe(viewLifecycleOwner){
            fragmentVideoMainBinding?.videoWatched?.text = it.toString()
        }
          simpleExoplayer.seekTo(playbackPosition.toLong())
        simpleExoplayer.playWhenReady = true
        fragmentVideoMainBinding?.exoplayerView?.player = simpleExoplayer
    }


    private fun preparePlayer(videoUrl: String?) {
        val uri = Uri.parse(videoUrl)
        val mediaSource = buildMediaSource(uri)
        simpleExoplayer.setMediaSource(mediaSource)
        simpleExoplayer.prepare()
    }

    private fun releasePlayer() {
        simpleExoplayer.release()
    }


    private fun buildMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(uri))
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }


}