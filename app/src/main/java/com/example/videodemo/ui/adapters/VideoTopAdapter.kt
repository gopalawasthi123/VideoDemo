package com.example.videodemo.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.videodemo.R
import com.example.videodemo.data.VideoX
import com.example.videodemo.databinding.TopVideoItemBinding

class VideoTopAdapter : ListAdapter<VideoX,VideoTopAdapter.VideoTopViewHolder>(VIDEOS_COMPARATOR) {

    class VideoTopViewHolder(private val topVideoItemBinding: TopVideoItemBinding): RecyclerView.ViewHolder(topVideoItemBinding.root){
        fun binds(videoX: VideoX?){
            Glide.with(topVideoItemBinding.root).load(videoX?.image)
                .placeholder(R.drawable.ic_launcher_background)
                .into(topVideoItemBinding.imageVideo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoTopViewHolder {
        val binding = TopVideoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VideoTopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoTopViewHolder, position: Int) {
        val item = getItem(position)
        holder.binds(item)
    }

    companion object {
        val VIDEOS_COMPARATOR = object : DiffUtil.ItemCallback<VideoX>(){
            override fun areItemsTheSame(oldItem: VideoX, newItem: VideoX): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: VideoX, newItem: VideoX): Boolean {
                return oldItem == newItem
            }

        }
    }

}