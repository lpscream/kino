package net.matrixhome.kino_test.ui

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import net.matrixhome.kino_test.model.Movie

class KinoAdapter : PagingDataAdapter<Movie, MovieViewHolder>(KINO_COMPARATOR) {

    private val TAG = "KinoAdapter_log"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val kinoItem = getItem(position)
        if (kinoItem != null){
            holder.bind(kinoItem)
        }
    }

    companion object{
        private val KINO_COMPARATOR = object : DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem

        }
    }
}


