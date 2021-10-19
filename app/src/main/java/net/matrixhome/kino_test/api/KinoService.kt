package net.matrixhome.kino_test.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

//didn't work without key in web, only in local area network
const val key = ""

interface KinoService {

    @Headers("Accept: application/json")
    @GET("?action=video")
    suspend fun searchMovies(
        @Query("sortby") sort_by: String = "added",
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("api_key") api_key: String = key
    ): RepoKinoResponse

    @Headers("Accept: application/json")
    @GET("?action=video")
    suspend fun getMoviesSortDesc(
        @Query("sort_desc") sort_desc: String = "added",
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("api_key") api_key: String = key
    ): RepoKinoResponse


    @Headers("Accept: application/json")
    @GET("?action=video")
    suspend fun getMoviesSortDesc(
        @Query("sort_desc") sort_desc: String = "added",
        @Query("category[]") category: List<String>?,
        @Query("genre_id[]") genre_id: List<String>?,
        @Query("made") made: String,
        @Query("year[]") year: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("api_key") api_key: String = key
    ): RepoKinoResponse




    @Headers("Accept: application/json")
    @GET("?action=video")
    suspend fun getMoviesSortDesc(
        @Query("sort_desc") sort_desc: String = "added",
        @Query("made") made: String = "",
        @Query("year[]") year: String = "",
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("api_key") api_key: String = key
    ): RepoKinoResponse






    companion object {
        private const val BASE_URL = "https://iptv.matrixhome.net/api/video/"
        private val TAG = "KinoService_log"

        fun create(): KinoService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC
            Log.d(TAG, "create: ${logger}")

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(KinoService::class.java)
        }
    }
}