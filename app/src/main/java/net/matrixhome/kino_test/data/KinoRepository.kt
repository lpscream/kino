package net.matrixhome.kino_test.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import net.matrixhome.kino_test.api.KinoService
import net.matrixhome.kino_test.model.Movie


private const val TAG = "KinoRepository_log"
class KinoRepository(private val service: KinoService) {

    fun getResultStream(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                KinoPagingSource(service = service, query = query)
            }).flow
    }


}