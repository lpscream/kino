package net.matrixhome.kino_test.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.matrixhome.kino_test.R
import net.matrixhome.kino_test.model.Movie

class MovieViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.filmName)
    private val yearCountry: TextView = view.findViewById(R.id.yearCountryTV)
    private val rating: TextView = view.findViewById(R.id.ratingTV)
    private val age: TextView = view.findViewById(R.id.ageTV)
    private val cover: ImageView = view.findViewById(R.id.cover)

    private var movie: Movie? = null


    private val TAG = "MovieViewHolder_log"

    init {

    }


    fun bind(movie: Movie){
        if (movie == null){
            name.text = "Loading"
        }else{
            showMovieData(movie)
        }
    }


    private fun showMovieData(movie: Movie){
        this.movie = movie
        Picasso.get().load(movie.cover_200).into(cover)
        name.text = movie.name
        yearCountry.text = "${movie.year}, ${movie.country}"
        if (!movie.rating.isNullOrEmpty()){
            rating.text = movie.rating
        }
        if (!movie.age.isNullOrEmpty()){
            age.text = movie.age
        }
    }


    companion object{
        fun create(parent: ViewGroup): MovieViewHolder{
            val view =  LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
            return MovieViewHolder(view)
        }
    }
}