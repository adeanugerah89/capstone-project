package com.anugerah.movieclub.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.anugerah.movieclub.MainActivity
import com.anugerah.movieclub.R
import com.anugerah.movieclub.core.data.source.Resource
import com.anugerah.movieclub.core.domain.model.Movie
import com.anugerah.movieclub.core.ui.MovieAdapter
import com.anugerah.movieclub.core.ui.RecyclerViewAdapterDelegate
import com.anugerah.movieclub.databinding.FragmentHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity == null) return

        (activity as MainActivity).hideNavBar(false)
        (activity as MainActivity).setSupportActionBar(binding.toolbar)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.app_name)

        loadData()
        binding.rvMovie.setHasFixedSize(true)
        binding.swpRefresh.setOnRefreshListener {
            loadData()
        }

    }

    private fun loadData() {
        viewModel.movie().observe(viewLifecycleOwner, {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.swpRefresh.isRefreshing = false
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        showItemList(it.data!!)
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun showItemList(list: List<Movie>) {
        val layoutManager = LinearLayoutManager(context)
        binding.rvMovie.layoutManager = layoutManager
        val movieAdapter = MovieAdapter()
        movieAdapter.setData(list)
        binding.rvMovie.adapter = movieAdapter

        movieAdapter.delegate = object : RecyclerViewAdapterDelegate<Movie> {
            override fun onClick(t: Movie) {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailMovieFragment(t)
                findNavController().navigate(action)
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(item: MenuItem) {
        when (item.itemId) {
            R.id.menu_search -> {
                val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.appBarLayout?.removeAllViewsInLayout()
        binding?.rvMovie?.adapter = null
        _binding = null
    }

}