package net.matrixhome.kino_test.model

import android.util.ArrayMap
import com.google.gson.annotations.SerializedName

data class Movie(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("description") val description: String,
    @field:SerializedName("time") val time: String,
    @field:SerializedName("director") val director: String,
    @field:SerializedName("actors") val actors: String,
    @field:SerializedName("added") val added: Int,
    @field:SerializedName("year") val year: String,
    @field:SerializedName("hd") val hd: Int,
    @field:SerializedName("kinopoisk_id") val kinopoisk_id: String,
    @field:SerializedName("age") val age: String,
    @field:SerializedName("country") val country: String,
    @field:SerializedName("date_premiere") val date_premiere: String,
    @field:SerializedName("translate_id") val translate_id: String,
    @field:SerializedName("serial_id") val serial_id: String? = null,
    @field:SerializedName("serial_count_seasons") val serial_count_seasons: String,
    @field:SerializedName("season_number") val season_number: String,
    @field:SerializedName("serial_name") val serial_name: String,
    @field:SerializedName("serial_o_name") val serial_o_name: String,
    @field:SerializedName("vote_percent") val vote_percent: String,
    @field:SerializedName("rating_vote_avg") val rating_vote_avg: String,
    @field:SerializedName("made") val made: String,
    @field:SerializedName("count_torrents") val count_torrents: String,
    @field:SerializedName("translate") val translate: String,
    @field:SerializedName("genres") val genres: String,
    @field:SerializedName("genres_ids") val genres_ids: ArrayList<String>,
    @field:SerializedName("cover") val cover: String,
    @field:SerializedName("category") val category: String,
    @field:SerializedName("original_name") val original_name: String,
    @field:SerializedName("rating") val rating: String,
    @field:SerializedName("views") val views: String,
    @field:SerializedName("views_month") val views_month: String,
    @field:SerializedName("mpaa") val mpaa: String,
    @field:SerializedName("serial_views") val serial_views: String,
    @field:SerializedName("video_views") val video_views: String,
    @field:SerializedName("series") val series: ArrayList<String> = arrayListOf(),
    @field:SerializedName("url") val url: String,
    @field:SerializedName("cover_200") val cover_200: String,
    @field:SerializedName("frames") val frames: ArrayList<String>,
    @field:SerializedName("tags") val tags: ArrayList<String>,
    @field:SerializedName("vote_count") val vote_count: VoteCount,
    @field:SerializedName("serial_vote_count") val serial_vote_count: SerialVoteCount,
    @field:SerializedName("rating_vote_count") val rating_vote_count: RatingVoteCount,
    @field:SerializedName("rating_kinopoisk") val rating_kinopoisk: RatingKinopoisk,
    @field:SerializedName("rating_imdb") val rating_imdb: RatingIMDB,
    @field:SerializedName("trailer_urls") val trailer_urls: ArrayList<String>
)



data class VoteCount(
    @field:SerializedName("count") val count: String,
    @field:SerializedName("count_like") val count_like: String,
    @field:SerializedName("count_dislike") val count_dislike: String,
    @field:SerializedName("count_neutral") val count_neutral: String
)


data class SerialVoteCount(
    @field:SerializedName("count") val count: String,
    @field:SerializedName("count_like") val count_like: String,
    @field:SerializedName("count_dislike") val count_dislike: String,
    @field:SerializedName("count_neutral") val count_neutral: String
)

data class RatingVoteCount(
    @field:SerializedName("count") val count: String,
    @field:SerializedName("sum") val sum: String,
    @field:SerializedName("value_count") val value_count: ArrayMap<String, String>
)


data class RatingKinopoisk(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("rating") val rating: String,
    @field:SerializedName("count") val count: String
)

data class RatingIMDB(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("rating") val rating: String,
    @field:SerializedName("count") val count: String
)
