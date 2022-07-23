package com.example.videodemo.ui.Fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.videodemo.databinding.FragmentVideoMainBinding
import com.example.videodemo.ui.SharedViewModel
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import dagger.hilt.android.AndroidEntryPoint


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
                }
            }
        })
    }


    private fun initializePlayer(videoMainFragment: VideoMainFragment) {
        simpleExoplayer = SimpleExoPlayer.Builder(videoMainFragment.requireContext()).build()
        sharedViewModel.videoData.observe(viewLifecycleOwner){
            preparePlayer(it)
        }

        fragmentVideoMainBinding?.exoplayerView?.player = simpleExoplayer
          simpleExoplayer.seekTo(playbackPosition.toLong())
        simpleExoplayer.playWhenReady = true
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