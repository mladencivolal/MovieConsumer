package com.example.movieconsumer.presentation.movies

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieconsumer.R
import com.example.movieconsumer.data.model.movie.Movie
import com.example.movieconsumer.databinding.MovieListItemBinding

class MoviesAdapter(recyclerView: RecyclerView) :
    RecyclerView.Adapter<MoviesAdapter.MyViewHolder>() {
    private val moviesList: MutableList<Movie> = mutableListOf()
    private var loading: Boolean = false
    lateinit var onItemClickListener: OnItemClickListener
    lateinit var onLoadMoreListener: OnLoadMoreListener
    lateinit var binding: MovieListItemBinding

    init {
        if (recyclerView.layoutManager is LinearLayoutManager) {
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val totalItemCount = linearLayoutManager.itemCount
                    val lastVisibleItem =
                        linearLayoutManager.findLastCompletelyVisibleItemPosition()
                    Log.i(
                        "MladenTag",
                        "onScrolled: moviesSize: ${moviesList.size} lastVisible: $lastVisibleItem"
                    )

                    if (!loading && totalItemCount - 5 <= lastVisibleItem && lastVisibleItem > moviesList.size - 5) {
                        onLoadMoreListener.onLoadMore()
                        loading = true
                    }
                }
            })
        }
    }

    fun setList(repos: List<Movie>) {
        moviesList.clear()
        moviesList.addAll(repos)
        notifyDataSetChanged()
    }

    fun updateList(repos: List<Movie>) {

        repos.forEach {
            this.moviesList.add(it)
            notifyItemInserted(moviesList.indexOf(it))
        }
    }

    fun setIsLoading(isLoading: Boolean) {
        loading = isLoading
    }


    interface OnLoadMoreListener {
        fun onLoadMore()
    }

    interface OnItemClickListener {
        fun onItemClick(movie: Movie, view: View)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.movie_list_item,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    override fun getItemCount(): Int = moviesList.size

    inner class MyViewHolder(
        val binding: MovieListItemBinding,
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(movie: Movie) {
            binding.imageView.setOnClickListener {
                onItemClickListener.onItemClick(moviesList[bindingAdapterPosition], it)
            }
            binding.titleTextView.text = shortenString(movie.title, 25)
            binding.descriptionTextView.text = shortenString(movie.overview, 200)
            binding.tvRating.text = "${movie.voteAverage}/10"
            binding.tvYear.text = "(${movie.releaseDate.substring(0, 4)})"
            val posterURL = "https://image.tmdb.org/t/p/w342" + movie.posterPath
            Glide.with(binding.imageView.context)
                .load(posterURL)
                .into(binding.imageView)
        }

        override fun onClick(p0: View?) {
            TODO("Not yet implemented")
        }
    }

    private fun shortenString(input: String, length: Int): String {
        var text = input
        if (input.length >= length) {
            text = input.substring(0..length-1)

            if (text.endsWith(" ") || text.endsWith(",")) {
               text.removeRange(text.length - 2, text.length - 1)
            }
            text+="..."
        }
        return text
    }
}