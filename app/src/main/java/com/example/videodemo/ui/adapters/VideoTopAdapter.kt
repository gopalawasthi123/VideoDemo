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
import com.example.videodemo.ui.listeners.ClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class VideoTopAdapter(private val listener : ClickListener) : ListAdapter<VideoX,VideoTopAdapter.VideoTopViewHolder>(VIDEOS_COMPARATOR) {


    class VideoTopViewHolder(private val topVideoItemBinding: TopVideoItemBinding): RecyclerView.ViewHolder(topVideoItemBinding.root){
        fun binds(videoX: VideoX?){
            Glide.with(topVideoItemBinding.root).load(videoX?.image)
                .placeholder(R.drawable.ic_launcher_background)
                .into(topVideoItemBinding.imageVideo)
        }

        fun updateProgress(progress : Int){
            topVideoItemBinding.circularProgress.progress = progress
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoTopViewHolder {
        val binding = TopVideoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val holder =VideoTopViewHolder(binding)
        binding.imageVideo.setOnClickListener{
            listener.videoItemClicked(position =holder.absoluteAdapterPosition )
        }
        return holder
    }

    override fun onBindViewHolder(holder: VideoTopViewHolder, position: Int) {
        val item = getItem(position)
        holder.binds(item)
    }

    override fun onBindViewHolder(
        holder: VideoTopViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        when{
            payloads.isEmpty() ->  super.onBindViewHolder(holder, position, payloads)
            else -> {
                if(payloads[0] == true ){
                    holder.updateProgress(getItem(position).videoProgress)
                }
            }
        }
    }

    fun updateProgress(duration : Int, position :Int){
       var item = getItem(position)
        item.videoProgress = duration
        notifyItemChanged(position,true)
    }

    companion object {
        val VIDEOS_COMPARATOR = object : DiffUtil.ItemCallback<VideoX>(){
            override fun areItemsTheSame(oldItem: VideoX, newItem: VideoX): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: VideoX, newItem: VideoX): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: VideoX, newItem: VideoX): Any? {
                return if(oldItem.videoProgress != newItem.videoProgress) true else null
            }
        }
    }

}