package com.example.movieconsumer.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieconsumer.R
import com.example.movieconsumer.data.model.movie_details.MovieDetails
import com.example.movieconsumer.databinding.ActivityDetailBinding
import com.example.movieconsumer.helpers.dateFormatter
import com.example.movieconsumer.helpers.formatCurrency
import com.example.movieconsumer.presentation.di.Injector
import kotlinx.android.synthetic.main.trailer_list_item.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailActivity : AppCompatActivity(), View.OnClickListener {
    @Inject
    lateinit var factory: DetailViewModelFactory
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding
    private lateinit var adapter: ActorsAdapter
    private lateinit var trailerAdapter: TrailersAdapter
    private lateinit var movieDetails: MovieDetails
    private var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        (application as Injector).createDetailSubComponent()
            .inject(this)

        detailViewModel = ViewModelProvider(this, factory)
            .get(DetailViewModel::class.java)

        initUI()
        populateWithIntentData()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (ytView != null) {
            ytView.release()
        }
    }

    private fun initUI() {
        binding.apply {
            bntCast.setOnClickListener(this@DetailActivity)
            bntTrailers.setOnClickListener(this@DetailActivity)
            recyclerView.layoutManager = LinearLayoutManager(this@DetailActivity)
            adapter = ActorsAdapter(recyclerView)
            recyclerView.adapter = adapter
        }
    }

    private fun populateWithIntentData() {
        movieId = intent.getIntExtra("movieId", 0)
        CoroutineScope(Dispatchers.Main).launch {
            movieDetails = detailViewModel.getMovieDetails(movieId)
            binding.apply {
                movieDetails.apply {
                    val moviePoster = resources.getString(
                        R.string.detail_activity_poster_link,
                        backdropPath
                    )
                    Glide.with(applicationContext)
                        .load(moviePoster)
                        .into(movieImage)
                    tvTitle.text = resources.getString(
                        R.string.detail_activity_title, title, releaseDate.substring(0, 4)
                    )
                    tvHeader.text = resources.getString(
                        R.string.detail_activity_duration_genre,
                        runtime.toString(),
                        genres.joinToString { it.name }
                    )
                    tvRating.text =
                        resources.getString(R.string.detail_activity_rating, voteAverage.toString())
                    tvRatingCount.text =
                        resources.getString(R.string.detail_activity_votes, voteCount.toString())
                    tvDescription.text = overview
                    tvReleaseDate.text = dateFormatter(releaseDate)
                    tvOriginalTitle.text = originalTitle
                    tvSpokenLanguages.text = spokenLanguages.joinToString { it.name }
                    tvBudget.text = formatCurrency(budget)
                    tvRevenue.text = formatCurrency(revenue)
                    ratingBar.rating = voteAverage.toFloat()

                    if (budget != 0 && revenue != 0) {
                        if (revenue > budget) {
                            tvRevenue.setTextColor(
                                ContextCompat.getColor(
                                    applicationContext,
                                    android.R.color.holo_green_light
                                )
                            )
                        } else {
                            tvRevenue.setTextColor(
                                ContextCompat.getColor(
                                    applicationContext,
                                    android.R.color.holo_red_light
                                )
                            )
                        }
                    }
                }
            }
        }
        getActorsFromMovie(movieId)
    }

    private fun getActorsFromMovie(movieId: Int) {
        val responseLiveData = detailViewModel.getActorsFromMovie(movieId)
        responseLiveData.observe(this, {
            if (it != null) {
                adapter.setList(it)
            }
        })
    }

    private fun getTrailersForMovie(movieId: Int) {
        val responseLiveData = detailViewModel.getTrailersForMovie(movieId)
        responseLiveData.observe(this, {
            if (it != null) {
                trailerAdapter.setList(it)
            }
        })
    }

    override fun onClick(p0: View?) {
        binding.apply {
            when (p0) {
                bntCast -> {
                    adapter = ActorsAdapter(recyclerView)
                    recyclerView.adapter = adapter
                    getActorsFromMovie(movieId)
                    bntCast.setBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.grey
                        )
                    )
                    bntTrailers.setBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            android.R.color.black
                        )
                    )
                }
                bntTrailers -> {
                    trailerAdapter = TrailersAdapter()
                    recyclerView.adapter = trailerAdapter
                    getTrailersForMovie(movieId)
                    bntTrailers.setBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.grey
                        )
                    )
                    bntCast.setBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            android.R.color.black
                        )
                    )
                }
            }
        }
    }
}

