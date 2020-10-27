package com.example.movieconsumer.presentation.movies

import android.app.ActionBar
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.core.view.marginBottom
import androidx.core.view.setMargins
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

class MoviesAdapter(private val recyclerView: RecyclerView, val context: Context) :
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
        if (recyclerView.layoutManager is GridLayoutManager) {
            binding.titleTextView.visible(false)
            binding.descriptionTextView.visible(false)
            binding.tvRating.visible(false)
            binding.tvYear.visible(false)
            binding.ivStar.visible(false)
            /*binding.cardView.radius = 0f
            val param = binding.cardView.layoutParams as ViewGroup.MarginLayoutParams
            param.setMargins(0, 0, 0, 0)

            binding.cardView.layoutParams = param*/
            val param = binding.listItemLayout.layoutParams as ViewGroup.MarginLayoutParams
            param.setMargins(0, 0, 0, 0)

            binding.listItemLayout.layoutParams = param
        }
    }

    override fun getItemCount(): Int = moviesList.size

    inner class MyViewHolder(
        val binding: MovieListItemBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.apply {
                movie.apply {
                    if(layoutManager !is GridLayoutManager) {
                        titleTextView.text = shortenString(title, 25)
                        descriptionTextView.text = shortenString(overview, 200)
                        tvRating.text = context.resources.getString(
                            R.string.detail_activity_rating,
                            voteAverage.toString()
                        )
                        tvYear.text = context.resources.getString(
                            R.string.movie_activity_year,
                            releaseDate.substring(0, 4)
                        )
                    }
                    val posterURL =
                        context.resources.getString(
                            R.string.movie_activity_poster_link,
                            posterPath
                        )
                    imageView.setOnClickListener {
                        onItemClickListener.onItemClick(moviesList[adapterPosition], it)
                    }
                    Glide.with(imageView.context)
                        .load(posterURL)
                        .into(imageView)
                }
            }
        }
    }
}