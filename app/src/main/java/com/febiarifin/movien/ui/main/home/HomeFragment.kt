package com.febiarifin.movien.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.febiarifin.movien.R
import com.febiarifin.movien.core.data.repository.Resource
import com.febiarifin.movien.core.domain.model.Movie
import com.febiarifin.movien.core.domain.model.MovieType
import com.febiarifin.movien.core.ui.adapter.MovieAdapter
import com.febiarifin.movien.core.ui.adapter.SliderAdapter
import com.febiarifin.movien.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showImageSlider()
        showNowPlayingMovies()
        showPopularMovies()
        showUpComingMovies()
        showTopRatedMovies()
        setUpTransformer()
    }

    private fun showImageSlider() {
        val adapter = SliderAdapter { movie, imageView ->
            moveToDetailMovie(movie, imageView)
        }
        viewModel.trendingMovies.observe(viewLifecycleOwner) { resources ->
            when (resources) {
                is Resource.Error -> {
                    Toast.makeText(requireActivity(), resources.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {}
                is Resource.Success -> {
                    adapter.submitList(resources.data.take(5))
                }
            }
        }

        binding?.contentHome?.apply {
            textNowPlaying.setOnClickListener { navigateMovies(MovieType.NOW_PLAYING, getString(R.string.now_playing)) }
            textPopular.setOnClickListener { navigateMovies(MovieType.POPULAR, getString(R.string.popular_movies)) }
            textTopRated.setOnClickListener { navigateMovies(MovieType.TOP_RATED, getString(R.string.top_rated_movies)) }
            textUpcoming.setOnClickListener { navigateMovies(MovieType.UPCOMING, getString(R.string.upcoming_movies)) }
        }

        binding?.contentHome?.vpImage?.apply {
            this.adapter = adapter
            offscreenPageLimit = 3
            clipToPadding = false
            clipChildren = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    swipe()
                }
            }
        }
    }

    private tailrec suspend fun ViewPager2.swipe() {
        delay(3000L)
        val numberItems = adapter?.itemCount ?: 0
        val lastIndex = if (numberItems > 0) numberItems - 1 else 0
        val nextItem = if (currentItem == lastIndex) 0 else currentItem + 1
        setCurrentItem(nextItem, true)
        swipe()
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        binding?.contentHome?.vpImage?.setPageTransformer(transformer)
    }

    private fun moveToDetailMovie(movie: Movie, imageView: ImageView) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(),
            Pair(imageView, "iv_movie")
        )
        val extras = ActivityNavigatorExtras(options)
        val data = HomeFragmentDirections.actionHomeFragmentToDetailActivity(movie)
        findNavController().navigate(data, extras)
    }

    private fun navigateMovies(movieType: MovieType, title: String) {
        val data = HomeFragmentDirections.actionHomeFragmentToMoviesFragment(movieType, title)
        findNavController().navigate(data)
    }

    private fun showNowPlayingMovies() {
        val adapter = MovieAdapter { movie, imageView ->
            moveToDetailMovie(movie, imageView)
        }
        binding?.contentHome?.apply {
            rvTrending.layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false
            )
            rvTrending.setHasFixedSize(true)
            rvTrending.adapter = adapter
        }
        viewModel.nowPlayingMovies.observe(viewLifecycleOwner) { resources ->
            when (resources) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    adapter.submitList(resources.data)
                }
            }
        }
    }

    private fun showPopularMovies() {
        val adapter = MovieAdapter { movie, imageView ->
            moveToDetailMovie(movie, imageView)
        }
        binding?.contentHome?.apply {
            rvPopular.layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false
            )
            rvPopular.setHasFixedSize(true)
            rvPopular.adapter = adapter
        }
        viewModel.popularMovies.observe(viewLifecycleOwner) { resources ->
            when (resources) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    adapter.submitList(resources.data)
                }
            }
        }
    }

    private fun showTopRatedMovies() {
        val adapter = MovieAdapter { movie, imageView ->
            moveToDetailMovie(movie, imageView)
        }
        binding?.contentHome?.apply {
            rvTopRated.layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false
            )
            rvTopRated.setHasFixedSize(true)
            rvTopRated.adapter = adapter
        }
        viewModel.topRatedMovies.observe(viewLifecycleOwner) { resources ->
            when (resources) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    adapter.submitList(resources.data)
                }
            }
        }
    }

    private fun showUpComingMovies() {
        val adapter = MovieAdapter { movie, imageView ->
            moveToDetailMovie(movie, imageView)
        }
        binding?.contentHome?.apply {
            rvUpcoming.layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false
            )
            rvUpcoming.setHasFixedSize(true)
            rvUpcoming.adapter = adapter
        }
        viewModel.upComingMovies.observe(viewLifecycleOwner) { resources ->
            when (resources) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    adapter.submitList(resources.data)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}