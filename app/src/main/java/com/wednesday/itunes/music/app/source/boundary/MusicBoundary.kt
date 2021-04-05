package com.wednesday.itunes.music.app.source.boundary

import androidx.core.net.toUri
import androidx.paging.PagedList
import com.wednesday.itunes.music.app.IO_EXECUTOR
import com.wednesday.itunes.music.app.NETWORK_EXECUTOR
import com.wednesday.itunes.music.app.source.api.ItunesApiService
import com.wednesday.itunes.music.app.source.api.Music
import com.wednesday.itunes.music.app.source.boundary.PagingRequestHelper.RequestType.AFTER
import com.wednesday.itunes.music.app.source.boundary.PagingRequestHelper.RequestType.INITIAL
import com.wednesday.itunes.music.app.source.cache.ItunesMusicCache
import com.wednesday.itunes.music.app.source.cache.MusicEntity
import com.wednesday.itunes.music.app.source.cache.SearchResultsEntity
import com.wednesday.itunes.music.app.util.toDate
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class MusicBoundary(
    private val searchKeyword: String,
    private val api: ItunesApiService,
    private val db: ItunesMusicCache,
    private val downloadCount: Int
) : PagedList.BoundaryCallback<MusicEntity>() {

    private var itemCount = 0
    private val musicDao = db.musicDao()
    val helper = PagingRequestHelper(IO_EXECUTOR)

    val itemCountSignal: (Int) -> Unit = {
        itemCount = it
    }

    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(INITIAL) {
            NETWORK_EXECUTOR.execute {
                api.searchMusic(
                    keyword = searchKeyword,
                    limit = downloadCount
                ).callbackSuccess(it) {
                    db.runInTransaction {
                        val musicEntities = it.body()?.musics?.map(Music::toEntity)
                        musicEntities?.run { musicDao.insertMusics(this) }
                        val searchEntities = musicEntities?.map { SearchResultsEntity(searchKeyword, it.id) }
                        searchEntities?.run { musicDao.insertSearches(this) }
                    }
                }
            }
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: MusicEntity) {
        helper.runIfNotRunning(AFTER) {
            NETWORK_EXECUTOR.execute {
                api.searchMusic(
                    keyword = searchKeyword,
                    limit = downloadCount,
                    offset = itemCount.toOffset(downloadCount)
                ).callbackSuccess(it) {
                    db.runInTransaction {
                        val musicEntities = it.body()?.musics?.map(Music::toEntity)
                        musicEntities?.run { musicDao.insertMusics(this) }
                        val searchEntities = musicEntities?.map { SearchResultsEntity(searchKeyword, it.id) }
                        searchEntities?.run { musicDao.insertSearches(this) }
                    }
                }
            }
        }
    }
}

private inline fun <T> Call<T>.callbackSuccess(callback: PagingRequestHelper.Callback, func: (Response<T>) -> Unit) {
    try {
        val response = execute()
        if (response.isSuccessful) {
            func(response)
            callback.recordSuccess()
        } else {
            callback.recordFailure(HttpException(response))
        }
    } catch (ex: IOException) {

        callback.recordFailure(ex)
    }
}

private fun Music.toEntity() = MusicEntity(
    trackId.toString(),
    trackName,
    trackPrice ?: 0.0,
    currency,
    shortDescription,
    longDescription,
    releaseDate!!.toDate(),
    primaryGenreName!!,
    artistName!!,
    previewUrl?.toUri(),
    artworkUrl100?.replace("100x100", "600x600")?.toUri() // we need high res image
)

private fun Int.toOffset(limit: Int) = if (this % limit == 0) {
    this / limit
} else {
    (this / limit) + 1
}
