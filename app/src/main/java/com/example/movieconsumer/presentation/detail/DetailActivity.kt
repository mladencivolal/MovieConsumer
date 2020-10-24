package com.example.movieconsumer.presentation.detail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieconsumer.R
import com.example.movieconsumer.data.model.actor.Actor
import com.example.movieconsumer.databinding.ActivityDetailBinding
import com.example.movieconsumer.presentation.di.Injector
import javax.inject.Inject

class DetailActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: DetailViewModelFactory
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding
    private lateinit var adapter: ActorsAdapter
    private lateinit var trailerAdapter: TrailersAdapter
    private lateinit var actor: Actor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        (application as Injector).createDetailSubComponent()
            .inject(this)

        detailViewModel = ViewModelProvider(this, factory)
            .get(DetailViewModel::class.java)

        initRecyclerView()

        populateWithIntentData()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        //adapter = ActorsAdapter(binding.recyclerView)
        trailerAdapter = TrailersAdapter()
        //binding.recyclerView.adapter = adapter
        binding.recyclerView.adapter = trailerAdapter
    }

    private fun populateWithIntentData() {
        val movieId = intent.getIntExtra("movieId", 0)
        val moviePoster = "https://image.tmdb.org/t/p/w342" + intent.getStringExtra("movieBackground")

            Glide.with(binding.movieImage.context)
                .load(moviePoster)
                .into(binding.movieImage)

            //getActorsFromMovie(movieId)
        getTrailersForMovie(movieId)
    }

    private fun getActorsFromMovie(movieId:Int) {
        val responseLiveData = detailViewModel.getActorsFromMovie(movieId)
        responseLiveData.observe(this, {
            if (it != null) {
                Log.i("recproblem", "ima nesto u listi: ${it.size}")
                adapter.setList(it)
            }
        })
    }

    private fun getTrailersForMovie(movieId:Int) {
        val responseLiveData = detailViewModel.getTrailersForMovie(movieId)
        responseLiveData.observe(this, {
            if (it != null) {
                Log.i("recproblem", "ima nesto u listi: ${it.size}")
                //adapter.setList(it)
                trailerAdapter.setList(it)
            }
        })
    }
}

