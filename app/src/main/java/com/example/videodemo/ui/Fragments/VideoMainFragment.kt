package com.example.videodemo.ui.Fragments

import android.graphics.SurfaceTexture
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
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
                    fragmentVideoMainBinding?.progressBar?.visibility = View.VISIBLE
                }
                else if(playbackState == Player.STATE_ENDED){
                    fragmentVideoMainBinding?.progressBar?.visibility = View.GONE
                }
                else if(playbackState == Player.STATE_READY){
                    fragmentVideoMainBinding?.progressBar?.visibility = View.GONE
                   lifecycleScope.launchWhenStarted {
                       getSeekBarPosition().observe(viewLifecycleOwner){
                           sharedViewModel._mutableProgress.value = it
                       }

                   }

                }

            }

            override fun onPositionDiscontinuity(
                oldPosition: Player.PositionInfo,
                newPosition: Player.PositionInfo,
                reason: Int
            ) {
                super.onPositionDiscontinuity(oldPosition, newPosition, reason)

            }



            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
            }
        })
    }

    private fun getSeekBarPosition() = liveData<Int> {
        while (true){
            val minDuration = 0.0f
            val currentpos: Int = (simpleExoplayer.currentPosition/1000).toInt()
            val maxDuration :Int = (simpleExoplayer.duration/1000).toInt()
            var durationPlayed = ((currentpos - minDuration)*100) /(maxDuration - minDuration)
            emit(durationPlayed.toInt())
            delay(1000L)
        }
    }

    private fun initializePlayer(videoMainFragment: VideoMainFragment) {
        simpleExoplayer = SimpleExoPlayer.Builder(videoMainFragment.requireContext()).build()
        sharedViewModel.videoData.observe(viewLifecycleOwner){
            preparePlayer(it)
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
        fragmentVideoMainBinding?.exoplayerView?.player = null
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