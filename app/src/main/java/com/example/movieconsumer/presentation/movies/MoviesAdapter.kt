package com.example.movieconsumer.presentation.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieconsumer.R
import com.example.movieconsumer.data.model.movie.Movie
import com.example.movieconsumer.databinding.MovieListItemBinding
import com.example.movieconsumer.helpers.shortenString
import com.example.movieconsumer.helpers.visible

class MoviesAdapter(recyclerView: RecyclerView, val context: Context) :
    RecyclerView.Adapter<MoviesAdapter.MyViewHolder>() {
    private val moviesList: MutableList<Movie> = mutableListOf()
    private var loading: Boolean = false
    lateinit var onItemClickListener: OnItemClickListener
    lateinit var onLoadMoreListener: OnLoadMoreListener
    lateinit var binding: MovieListItemBinding
    var layoutManager: LinearLayoutManager

    init {
        var buffer = 5

        if (recyclerView.layoutManager is LinearLayoutManager) {
            layoutManager = recyclerView.layoutManager as LinearLayoutManager
        } else {
            layoutManager = recyclerView.layoutManager as GridLayoutManager
            buffer = 24
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem =
                    layoutManager.findLastCompletelyVisibleItemPosition()

                if (!loading && totalItemCount - buffer <= lastVisibleItem && lastVisibleItem > moviesList.size - buffer) {
                    onLoadMoreListener.onLoadMore()
                    loading = true
                }
            }
        })
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
                    if (layoutManager !is GridLayoutManager) {
                        tvTitle.text = shortenString(title, 25)
                        etvDescription.text = overview
                        tvRating.text = context.resources.getString(
                            R.string.detail_activity_rating,
                            voteAverage.toString()
                        )
                        tvYear.text = context.resources.getString(
                            R.string.movie_activity_year,
                            releaseDate.substring(0, 4)
                        )
                    } else {
                        val param =
                            listItemLayout.layoutParams as ViewGroup.MarginLayoutParams
                        param.setMargins(0, 0, 0, 0)
                        listItemLayout.layoutParams = param

                        tvTitle.visible(false)
                        etvDescription.visible(false)
                        tvRating.visible(false)
                        tvYear.visible(false)
                        ivStar.visible(false)
                    }
                    val posterURL =
                        context.resources.getString(
                            R.string.movie_activity_poster_link,
                            posterPath
                        )
                    Glide.with(ivMovie.context)
                        .load(posterURL)
                        .into(ivMovie)
                    ivMovie.setOnClickListener {
                        onItemClickListener.onItemClick(moviesList[adapterPosition], it)
                    }
                }
            }
        }
    }
}