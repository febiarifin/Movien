package com.febiarifin.movien.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.febiarifin.movien.core.databinding.ItemMovieGridBinding
import com.febiarifin.movien.core.domain.model.Movie
import com.febiarifin.movien.core.ui.adapter.MovieAdapter.Companion.DIFF_CALLBACK
import com.febiarifin.movien.core.utils.Extentions.showImageInto

class MoviePagingAdapter(
    private val data: (Movie, ImageView) -> Unit
) : PagingDataAdapter<Movie, MoviePagingAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let { holder.bind(it) }
    }

    inner class MovieViewHolder(private val binding: ItemMovieGridBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.apply {
                ivMovie.showImageInto(itemView.context, movie.posterPath)
                ivMovie.setOnClickListener {
                    data.invoke(movie, ivMovie)
                }
            }
        }
    }
}