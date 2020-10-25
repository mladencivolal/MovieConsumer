package com.example.movieconsumer.presentation.movies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieconsumer.R
import com.example.movieconsumer.data.model.movie.Movie
import com.example.movieconsumer.databinding.ActivityMovieBinding
import com.example.movieconsumer.presentation.detail.DetailActivity
import com.example.movieconsumer.presentation.di.Injector
import javax.inject.Inject

class MoviesActivity : AppCompatActivity(), MoviesAdapter.OnLoadMoreListener,
    MoviesAdapter.OnItemClickListener, View.OnClickListener {
    @Inject
    lateinit var factory: MoviesViewModelFactory
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var binding: ActivityMovieBinding
    private lateinit var adapter: MoviesAdapter

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
        adapter = MoviesAdapter(binding.movieRecyclerView)

        adapter.apply {
            onLoadMoreListener = this@MoviesActivity
            onItemClickListener = this@MoviesActivity
        }
        binding.apply {
            movieRecyclerView.adapter = adapter
            movieRecyclerView.setHasFixedSize(true)
            movieRecyclerView.setItemViewCacheSize(50)
        }
    }

    private fun discoverMovies() {
        val responseLiveData = moviesViewModel.getMovies()
        responseLiveData.observe(this, Observer {
            if (it != null) {
                adapter.setList(it)
                adapter.notifyDataSetChanged()
                binding.movieProgressBar.visibility = View.GONE
            } else {
                binding.movieProgressBar.visibility = View.GONE
            }
        })
    }

    private fun loadMoreMovies() {
        val responseLiveData = moviesViewModel.loadMoreMovies()
        responseLiveData.observe(this, Observer {
            if (it != null) {
                adapter.updateList(it)
                adapter.notifyDataSetChanged()
                binding.movieProgressBar.visibility = View.GONE
            } else {
                binding.movieProgressBar.visibility = View.GONE
            }
        })
    }

    override fun onLoadMore() {
        Log.i("MladenTag", "onLoadMore: ")
        binding.movieProgressBar.visibility = View.VISIBLE
        val responseLiveData = moviesViewModel.loadMoreMovies()
        responseLiveData.observe(this, {
            if (it != null) {
                adapter.setIsLoading(false)
                if (it.size > 5) adapter.updateList(it)
                binding.movieProgressBar.visibility = View.INVISIBLE
            }
        })
    }

    override fun onItemClick(movie: Movie, view: View) {
        when (view.id) {
            R.id.imageView -> launchDetailActivity(movie)
        }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    private fun launchDetailActivity(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
            .putExtra("movieId", movie.id)
        startActivity(intent)
    }

}