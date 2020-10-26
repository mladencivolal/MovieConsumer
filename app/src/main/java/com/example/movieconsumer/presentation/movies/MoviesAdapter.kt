package com.example.movieconsumer.presentation.movies

import android.content.Context
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
import com.example.movieconsumer.helpers.shortenString

class MoviesAdapter(recyclerView: RecyclerView, val context: Context) :
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
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.apply {
                movie.apply {
                    imageView.setOnClickListener {
                        onItemClickListener.onItemClick(moviesList[adapterPosition], it)
                    }
                    titleTextView.text = shortenString(title, 25)
                    descriptionTextView.text = shortenString(overview, 200)
                    tvRating.text = context.resources.getString(R.string.detail_activity_rating, voteAverage.toString())
                    tvYear.text = context.resources.getString(R.string.movie_activity_year, releaseDate.substring(0, 4))
                    val posterURL = context.resources.getString(R.string.movie_activity_poster_link, posterPath)
                    Glide.with(imageView.context)
                        .load(posterURL)
                        .into(imageView)
                }
            }
        }
    }
}