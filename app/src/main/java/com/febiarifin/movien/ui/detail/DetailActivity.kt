package com.febiarifin.movien.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.ActivityNavigator
import androidx.navigation.navArgs
import com.febiarifin.movien.R
import com.febiarifin.movien.core.domain.model.Movie
import com.febiarifin.movien.core.utils.DataMapper
import com.febiarifin.movien.core.utils.Extentions.showImageInto
import com.febiarifin.movien.core.utils.Extentions.toAnotherDate
import com.febiarifin.movien.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val navArgs: DetailActivityArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()
    private var menu: Menu? = null
    private var statusFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpToolbar()
        setUpView()
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun finish() {
        super.finish()
        ActivityNavigator.applyPopAnimationsToPendingTransition(this)
    }

    private fun setUpView() {
        val movie = navArgs.movie
        populateDetailMovie(movie)
        viewModel.isFavorite(movie.id).observe(this, favoriteObserver)
    }

    private val favoriteObserver = Observer<Boolean> { isFavorite ->
        this.statusFavorite = isFavorite
        setMovieToFavorite(isFavorite)
    }

    private fun populateDetailMovie(movie: Movie) {
        binding.content.apply {
            ivBackdrop.showImageInto(this@DetailActivity, movie.backdropPath)
            ivPoster.showImageInto(this@DetailActivity, movie.posterPath)
            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            tvDate.text = movie.releaseDate?.toAnotherDate()
            tvLanguage.text = movie.originalLanguage
            tvRating.text = movie.voteAverage.toString()
            DataMapper.mapGenreIdToGenre(movie.genreIds)
                .filterNotNull()
                .take(3)
                .forEach { tvGenres.append(" $it \u2022 ") }
        }
    }

    private fun setMovieToFavorite(favorite: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.btn_favorite)
        if (favorite) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_filled)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        viewModel.isFavorite(navArgs.movie.id).observe(this, favoriteObserver)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.btn_favorite -> {
                statusFavorite = !statusFavorite
                val movie = navArgs.movie
                viewModel.update(movie.id, statusFavorite)
                setMovieToFavorite(statusFavorite)
                true
            }
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

}
