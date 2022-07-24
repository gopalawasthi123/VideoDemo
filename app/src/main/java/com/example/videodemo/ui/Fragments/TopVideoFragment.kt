package com.example.videodemo.ui.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.videodemo.databinding.FragmentTopVideoBinding
import com.example.videodemo.ui.SharedViewModel
import com.example.videodemo.ui.adapters.VideoTopAdapter
import com.example.videodemo.ui.listeners.ClickListener
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 * Use the [TopVideoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class TopVideoFragment : Fragment() ,ClickListener{
    private val sharedViewModel : SharedViewModel by activityViewModels()
    private lateinit var adapter : VideoTopAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val topVideoBinding = FragmentTopVideoBinding.inflate(layoutInflater, container, false)

        adapter = VideoTopAdapter(this)
        topVideoBinding.recyclerTopView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        topVideoBinding.recyclerTopView.adapter = adapter

        sharedViewModel.videoList.observe(viewLifecycleOwner){ video ->
            adapter.submitList(video)
            sharedViewModel.getVideoData(adapter.getAdapterViewClickedPosition())
        }


        lifecycleScope.launchWhenStarted {
                sharedViewModel._mutableProgress.observe(viewLifecycleOwner){
                    adapter.updateProgress(it,adapter.getAdapterViewClickedPosition())
                }
        }
        return topVideoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.getVideosFromVideoRepo()

    }

    override fun videoItemClicked(position: Int) {
       val item = adapter.currentList?.get(position)
        if(item != null)
        sharedViewModel.getVideoData(position)
    }

}