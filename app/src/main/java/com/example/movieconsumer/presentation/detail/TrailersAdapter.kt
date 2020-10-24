package com.example.movieconsumer.presentation.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieconsumer.R
import com.example.movieconsumer.data.model.Trailer.Trailer
import com.example.movieconsumer.data.model.actor.Actor
import com.example.movieconsumer.databinding.TrailerListItemBinding
import com.example.movieconsumer.presentation.movies.MoviesAdapter

class TrailersAdapter : RecyclerView.Adapter<TrailersAdapter.MyViewHolder>() {
    private val trailersList: MutableList<Trailer> = mutableListOf()
    lateinit var onItemClickListener: MoviesAdapter.OnItemClickListener
    lateinit var binding: TrailerListItemBinding


    inner class MyViewHolder(
        val binding: TrailerListItemBinding,
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
            binding.webView.settings.javaScriptEnabled = true
            binding.webView.webChromeClient = object : WebChromeClient() {
            }

        }

        fun bind(trailer: Trailer) {
            //val youtubeLink = "https://www.youtube.com/watch?v=" + trailer.key
            val youtubeLink =
                "<iframe width=\"100%\" height=\"100%\" src=\"https://www" +
                        ".youtube.com/embed/${trailer.key}\" frameborder=\"0\" allowfullscreen></iframe>"
            binding.webView.loadData(youtubeLink, "text/html", "utf-8")
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