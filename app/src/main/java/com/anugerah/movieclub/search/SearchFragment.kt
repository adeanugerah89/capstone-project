package com.anugerah.movieclub.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.anugerah.movieclub.MainActivity
import com.anugerah.movieclub.core.domain.model.Movie
import com.anugerah.movieclub.core.ui.MovieAdapter
import com.anugerah.movieclub.core.ui.RecyclerViewAdapterDelegate
import com.anugerah.movieclub.databinding.FragmentSearchBinding
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModel()
    private val movieAdapter = MovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity == null) return

        (activity as MainActivity).hideNavBar(true)

        binding.rvMovie.setHasFixedSize(true)
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                lifecycleScope.launch {
                    viewModel.queryChannel.send(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        viewModel.searchResult.observe(viewLifecycleOwner, {
            if (it != null) {
                val movieList = arrayListOf<Movie>()
                it.map { movie ->
                    movieList.add(movie)
                }
                showList(movieList)
            }
        })
    }

    private fun showList(list: List<Movie>) {
        binding.rvMovie.layoutManager = LinearLayoutManager(context)
        binding.rvMovie.setHasFixedSize(true)
        movieAdapter.setData(list)
        binding.rvMovie.adapter = movieAdapter

        movieAdapter.delegate = object : RecyclerViewAdapterDelegate<Movie> {
            override fun onClick(t: Movie) {
                val action = SearchFragmentDirections.actionSearchFragmentToDetailMovieFragment(t)
                findNavController().navigate(action)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}