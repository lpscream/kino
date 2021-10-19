package net.matrixhome.kino_test.api

import com.google.gson.annotations.SerializedName
import net.matrixhome.kino_test.model.Movie

data class RepoKinoResponse(
    @SerializedName("status") var status: String = "",
    @SerializedName("results") var results: List<Movie> = emptyList()
)
