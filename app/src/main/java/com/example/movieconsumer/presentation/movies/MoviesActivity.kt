package com.example.movieconsumer.presentation.movies

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieconsumer.R
import com.example.movieconsumer.data.model.movie.Movie
import com.example.movieconsumer.databinding.ActivityMovieBinding
import com.example.movieconsumer.helpers.visible
import com.example.movieconsumer.presentation.detail.DetailActivity
import com.example.movieconsumer.presentation.di.Injector
import javax.inject.Inject

class MoviesActivity : AppCompatActivity(), MoviesAdapter.OnLoadMoreListener,
    MoviesAdapter.OnItemClickListener {
    @Inject
    lateinit var factory: MoviesViewModelFactory
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var binding: ActivityMovieBinding
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie)
        (application as Injector).createMovieSubComponent()
            .inject(this)

        moviesViewModel = ViewModelProvider(this, factory)
            .get(MoviesViewModel::class.java)

        initRecyclerView()
        discoverMovies()
    }

    private fun initRecyclerView() {
        binding.movieRecyclerView.layoutManager = LinearLayoutManager(this)
        moviesAdapter = MoviesAdapter(binding.movieRecyclerView, this)

        moviesAdapter.apply {
            onLoadMoreListener = this@MoviesActivity
            onItemClickListener = this@MoviesActivity
        }
        binding.apply {
            movieRecyclerView.apply {
                adapter = moviesAdapter
                setHasFixedSize(true)
                setItemViewCacheSize(50)
            }

        }
    }

    private fun discoverMovies() {
        val responseLiveData = moviesViewModel.getMovies()
        responseLiveData.observe(this, {
            if (it != null) {
                moviesAdapter.setList(it)
                moviesAdapter.notifyDataSetChanged()
                binding.movieProgressBar.visible(false)
            } else {
                binding.movieProgressBar.visible(false)
            }
        })
    }

    private fun loadMoreMovies() {
        binding.movieProgressBar.visible(true)
        val responseLiveData = moviesViewModel.loadMoreMovies()
        responseLiveData.observe(this, {
            if (it != null) {
                moviesAdapter.setIsLoading(false)
                if (it.size > 5) moviesAdapter.updateList(it)
                binding.movieProgressBar.visible(false)
            }
        })
    }

    override fun onLoadMore() {
       loadMoreMovies()
    }

    override fun onItemClick(movie: Movie, view: View) {
        when (view.id) {
            R.id.imageView -> launchDetailActivity(movie)
        }
    }

    private fun launchDetailActivity(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
            .putExtra("movieId", movie.id)
        startActivity(intent)
    }
}