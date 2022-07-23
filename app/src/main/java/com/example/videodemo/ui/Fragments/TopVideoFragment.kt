package com.example.videodemo.ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.videodemo.databinding.FragmentTopVideoBinding
import com.example.videodemo.ui.SharedViewModel
import com.example.videodemo.ui.adapters.VideoTopAdapter
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 * Use the [TopVideoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class TopVideoFragment : Fragment() {
    private val sharedViewModel : SharedViewModel by activityViewModels()
    private lateinit var adapter : VideoTopAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val topVideoBinding = FragmentTopVideoBinding.inflate(layoutInflater, container, false)

        adapter = VideoTopAdapter()
        topVideoBinding.recyclerTopView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        topVideoBinding.recyclerTopView.adapter = adapter

        sharedViewModel.videoList.observe(viewLifecycleOwner){ video ->
            adapter.submitList(video)
            sharedViewModel.getVideoData(0)
        }



        return topVideoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.getVideosFromVideoRepo()

    }
}