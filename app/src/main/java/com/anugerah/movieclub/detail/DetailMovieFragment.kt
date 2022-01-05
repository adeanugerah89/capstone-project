package com.anugerah.movieclub.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.anugerah.movieclub.BuildConfig
import com.anugerah.movieclub.MainActivity
import com.anugerah.movieclub.core.domain.model.Movie
import com.anugerah.movieclub.databinding.FragmentDetailMovieBinding
import com.bumptech.glide.Glide
import org.koin.android.viewmodel.ext.android.viewModel

class DetailMovieFragment : Fragment() {

    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailMovieFragmentArgs>()
    private val viewModel: DetailMovieViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity == null) return

        (activity as MainActivity).hideNavBar(true)
        (activity as MainActivity).supportActionBar?.hide()

        (activity as MainActivity).apply {
            setSupportActionBar(binding.toolbar)
            if (supportActionBar != null) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                binding.toolbar.setNavigationOnClickListener { onBackPressed() }
            }

        }
        val movie = args.movie
        showDetailMovie(movie)
    }

    private fun showDetailMovie(detailMovie: Movie?) {
        detailMovie?.let {
            binding.content.tvDetailDescription.text = detailMovie.overview
            Glide.with(this@DetailMovieFragment)
                .load(BuildConfig.IMAGE_URL + detailMovie.backdropPath)
                .into(binding.ivDetailImage)

            var statusFavorite = detailMovie.isFavorite
            setStatusFavorite(statusFavorite)
            binding.fab.setOnClickListener {
                statusFavorite = !statusFavorite
                viewModel.setFavoriteMovie(detailMovie, statusFavorite)
                setStatusFavorite(statusFavorite)
            }
            binding.toolbar.title = detailMovie.title
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        binding.fab.isSelected = statusFavorite
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.appBar?.removeAllViewsInLayout()
        _binding = null
    }
}