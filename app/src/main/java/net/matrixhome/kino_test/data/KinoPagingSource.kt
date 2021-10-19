package net.matrixhome.kino_test.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import net.matrixhome.kino_test.api.KinoService
import net.matrixhome.kino_test.model.Movie
import retrofit2.HttpException
import java.io.IOException


const val NETWORK_PAGE_SIZE = 20
private const val INITIAL_LOAD_SIZE = 0
private val TAG = "KinoPagingSource_log"

class KinoPagingSource(private val service: KinoService, private val query: String): PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: INITIAL_LOAD_SIZE
        val offset = if (params.key != null)((position) * NETWORK_PAGE_SIZE) else INITIAL_LOAD_SIZE
        val apiQuery = query
        return try {
            val jsonResponce = service.searchMovies(query = apiQuery, limit = params.loadSize, offset = offset)
            //val jsonResponce = service.getMoviesSortDesc(limit = params.loadSize, offset = offset)
            val repos = sortMovieArray(inArray = jsonResponce.results)
            val nextKey = if (repos.isEmpty()){
                null
            }else{
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = repos,
                prevKey = if (position == INITIAL_LOAD_SIZE) null else position - 1,
                nextKey = nextKey)
        }catch (exception : IOException){
            Log.d(TAG, "load: $exception")
            return LoadResult.Error(exception)
        }catch (exception: HttpException){
            Log.d(TAG, "load: $exception")
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }



    private fun sortMovieArray(inArray: List<Movie>): List<Movie> {
        val outArray: ArrayList<Movie> = inArray as ArrayList<Movie>
        for (i in outArray.indices) {
            if (i < outArray.size){
                if (!outArray[i].serial_id.isNullOrEmpty()){
                    var j: Int = 1
                    while (j <= outArray.size){
                        if (i < outArray.size && j < outArray.size){
                            if (!outArray[j].serial_id.isNullOrEmpty()){
                                if (outArray[i].serial_id == outArray[j].serial_id){
                                    if (j != i){
                                        //
                                        if (outArray[i].season_number.toInt() > outArray[j].season_number.toInt()){
                                            val movies = outArray[i]
                                            outArray[i] = outArray[j]
                                            outArray[j] = movies
                                        }
                                        //
                                        outArray.removeAt(i)
                                        if (j != 0){
                                            j = j - 1
                                        }
                                    }
                                }
                            }
                        }
                        j++
                    }
                }
            }
        }
        return outArray as List<Movie>
    }
}