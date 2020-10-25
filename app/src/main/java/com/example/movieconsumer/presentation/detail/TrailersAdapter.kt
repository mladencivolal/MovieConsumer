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

        init {
           /* itemView.setOnClickListener(this)
            binding.ytView.settings.javaScriptEnabled = true
            binding.ytView.webChromeClient = object : WebChromeClient() {
            }*/
        }

        fun bind(trailer: Trailer) {
         /*   val youtubeLink =
                "<iframe width=\"100%\" height=\"100%\" src=\"https://www" +
                        ".youtube.com/embed/${trailer.key}\"  frameborder=\"0\" allowfullscreen></iframe>"
            val link = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.shacknews.com/\" frameborder=\"0\" allowfullscreen></iframe>"
            binding.ytView.setBackgroundResource(android.R.color.black)
            binding.ytView.loadData(youtubeLink, "text/html", "utf-8")*/
            //binding.ytView.vide
            val iFramePlayerOptions = IFramePlayerOptions.Builder()
                .controls(1)
                .build()

            val youTubePlayerListener = object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val videoId = trailer.key
                    // youTubePlayer.loadVideo(videoId, 0f)
                    youTubePlayer.cueVideo(videoId, 0f)
                    // youTubePlayer.pause()
                }
            }
            binding.ytView.enableAutomaticInitialization = false
            binding.ytView.initialize(youTubePlayerListener, false, iFramePlayerOptions)

          /*  binding.ytView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val videoId = trailer.key
                    // youTubePlayer.loadVideo(videoId, 0f)
                    youTubePlayer.cueVideo(videoId, 0f)
                    // youTubePlayer.pause()
                }
            })*/
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