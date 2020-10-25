package com.example.movieconsumer.presentation.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieconsumer.R
import com.example.movieconsumer.data.model.movie_details.MovieDetails
import com.example.movieconsumer.databinding.ActivityDetailBinding
import com.example.movieconsumer.presentation.di.Injector
import kotlinx.android.synthetic.main.trailer_list_item.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    @Inject
    lateinit var factory: DetailViewModelFactory
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding
    private lateinit var adapter: ActorsAdapter
    private lateinit var trailerAdapter: TrailersAdapter
    lateinit var movieDetails: MovieDetails
    private var movieId: Int = 0
    var adapters = arrayOf("Cast", "Trailers")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        (application as Injector).createDetailSubComponent()
            .inject(this)

        detailViewModel = ViewModelProvider(this, factory)
            .get(DetailViewModel::class.java)

        binding.adapterSpinner.onItemSelectedListener = this

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, adapters)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        binding.adapterSpinner.adapter = aa

        initRecyclerView()

        populateWithIntentData()
    }

    override fun onDestroy() {
        super.onDestroy()
        ytView.release()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ActorsAdapter(binding.recyclerView)
        binding.recyclerView.adapter = adapter
    }

    private fun populateWithIntentData() {
        movieId = intent.getIntExtra("movieId", 0)
        CoroutineScope(Dispatchers.Main).launch {
            movieDetails = detailViewModel.getMovieDetails(movieId)

            binding.apply {
                val moviePoster =
                    "https://image.tmdb.org/t/p/w342" + movieDetails.backdropPath
                Glide.with(movieImage.context)
                    .load(moviePoster)
                    .into(movieImage)
                tvTitle.text = movieDetails.title
                tvHeader.text =
                    "${movieDetails.runtime}min • ${movieDetails.genres.joinToString { it.name }}"
                tvRating.text = "${movieDetails.voteAverage}/10"
                tvRatingCount.text = "(${movieDetails.voteCount} votes)"
                tvDescription.text = movieDetails.overview




                tvReleaseDate.text = movieDetails.releaseDate
                tvOriginalTitle.text = movieDetails.originalTitle
                tvSpokenLanguages.text = movieDetails.spokenLanguages.joinToString { it.name }
                tvBudget.text = "${movieDetails.budget}$"
                tvRevenue.text = "${movieDetails.revenue}$"
                ratingBar.rating = movieDetails.voteAverage.toFloat()
            }
        }
        getActorsFromMovie(movieId)
    }

    private fun getActorsFromMovie(movieId: Int) {
        val responseLiveData = detailViewModel.getActorsFromMovie(movieId)
        responseLiveData.observe(this, {
            if (it != null) {
                Log.i("recproblem", "ima nesto u listi: ${it.size}")
                adapter.setList(it)
            }
        })
    }

    private fun getTrailersForMovie(movieId: Int) {
        val responseLiveData = detailViewModel.getTrailersForMovie(movieId)
        responseLiveData.observe(this, {
            if (it != null) {
                Log.i("recproblem", "ima nesto u listi: ${it.size}")
                //adapter.setList(it)
                trailerAdapter.setList(it)
            }
        })
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when (p2) {
            0 -> {
                adapter = ActorsAdapter(binding.recyclerView)
                binding.recyclerView.adapter = adapter
                getActorsFromMovie(movieId)
            }
            1 -> {
                trailerAdapter = TrailersAdapter()
                binding.recyclerView.adapter = trailerAdapter
                getTrailersForMovie(movieId)
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}

