package com.example.movieconsumer.presentation.movies

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
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
    private var showAsGrid = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie)
        (application as Injector).createMovieSubComponent()
            .inject(this)

        moviesViewModel = ViewModelProvider(this, factory)
            .get(MoviesViewModel::class.java)

        initRecyclerView(false)
        discoverMovies()
    }

    private fun initRecyclerView(showGrid: Boolean) {
        if (showGrid) {
            binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
        } else {
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
        }

        moviesAdapter = MoviesAdapter(binding.recyclerView, this)

        moviesAdapter.apply {
            onLoadMoreListener = this@MoviesActivity
            onItemClickListener = this@MoviesActivity
        }
        binding.apply {
            recyclerView.apply {
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
                binding.progressBar.visible(false)
            } else {
                binding.progressBar.visible(false)
            }
        })
    }

    private fun loadMoreMovies() {
        binding.progressBar.visible(true)
        val responseLiveData = moviesViewModel.loadMoreMovies()
        responseLiveData.observe(this, {
            if (it != null) {
                moviesAdapter.setIsLoading(false)
                if (it.size > 5) moviesAdapter.updateList(it)
                binding.progressBar.visible(false)
            }
        })
    }

    override fun onLoadMore() {
        loadMoreMovies()
    }

    override fun onItemClick(movie: Movie, view: View) {
        when (view.id) {
            R.id.ivMovie -> launchDetailActivity(movie)
        }
    }

    private fun launchDetailActivity(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
            .putExtra("movieId", movie.id)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_show_as -> {
                initRecyclerView(showAsGrid)
                discoverMovies()
                showAsGrid = if (showAsGrid) {
                    item.setIcon(R.drawable.ic_list)
                    !showAsGrid
                } else {
                    item.setIcon(R.drawable.ic_grid)
                    !showAsGrid
                }
                return true
            }
            R.id.action_refresh -> {
                discoverMovies()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}