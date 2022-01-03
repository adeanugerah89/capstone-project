package com.anugerah.movieclub.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anugerah.movieclub.core.BuildConfig
import com.anugerah.movieclub.core.R
import com.anugerah.movieclub.core.databinding.MovieItemBinding
import com.anugerah.movieclub.core.domain.model.Movie
import com.bumptech.glide.Glide
import java.util.ArrayList

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ListViewHolder>() {

    private var listData = ArrayList<Movie>()
    var delegate: RecyclerViewAdapterDelegate<Movie>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        )

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = MovieItemBinding.bind(itemView)
        fun bind(data: Movie) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(BuildConfig.IMAGE_URL + data.posterPath)
                    .into(imgItemCard)
                tvMovieTitle.text = data.title
                tvRating.text = data.voteAverage.toString()

                root.setOnClickListener {
                    delegate?.onClick(data)
                }
            }
        }
    }

    class MyDiffCallback(
        private val oldMovieData: List<Movie>,
        private val newMovieData: List<Movie>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldMovieData.size
        }

        override fun getNewListSize(): Int {
            return newMovieData.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            // In the real world you need to compare something unique like id
            return oldMovieData[oldItemPosition] == newMovieData[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            // This is called if areItemsTheSame() == true;
            return oldMovieData[oldItemPosition] == newMovieData[newItemPosition]
        }
    }

    fun setData(newListData: List<Movie>?) {
        if (newListData == null) return

        val diffCallback = MyDiffCallback(this.listData, newListData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        listData.clear()
        listData.addAll(newListData)

        diffResult.dispatchUpdatesTo(this)
    }
}