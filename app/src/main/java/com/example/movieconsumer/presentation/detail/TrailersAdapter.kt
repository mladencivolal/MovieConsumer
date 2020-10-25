package com.example.movieconsumer.presentation.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieconsumer.R
import com.example.movieconsumer.data.model.Trailer.Trailer
import com.example.movieconsumer.databinding.TrailerListItemBinding
import com.example.movieconsumer.presentation.movies.MoviesAdapter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions





class TrailersAdapter : RecyclerView.Adapter<TrailersAdapter.MyViewHolder>() {
    private val trailersList: MutableList<Trailer> = mutableListOf()
    lateinit var onItemClickListener: MoviesAdapter.OnItemClickListener
    lateinit var binding: TrailerListItemBinding


    inner class MyViewHolder(
        val binding: TrailerListItemBinding,
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(trailer: Trailer) {
            binding.ytView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val videoId = trailer.key
                    youTubePlayer.cueVideo(videoId, 0f)
                }
            })
            binding.tvTrailerTitle.text = trailer.name
        }

        override fun onClick(p0: View?) {
            TODO("Not yet implemented")
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrailersAdapter.MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.trailer_list_item,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrailersAdapter.MyViewHolder, position: Int) {
        holder.bind(trailersList[position])
    }

    override fun getItemCount(): Int = trailersList.size

    fun setList(trailers: List<Trailer>) {
        trailersList.clear()
        trailersList.addAll(trailers)
        notifyDataSetChanged()
    }
}