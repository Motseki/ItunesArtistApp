package com.wednesday.itunes.music.app.source.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import com.wednesday.itunes.music.app.IO_EXECUTOR
import com.wednesday.itunes.music.app.source.api.ItunesApi
import com.wednesday.itunes.music.app.source.boundary.BoundaryBundle
import com.wednesday.itunes.music.app.source.boundary.MusicBoundary
import com.wednesday.itunes.music.app.source.cache.ItunesMusicCache
import com.wednesday.itunes.music.app.source.cache.MusicEntity
import com.wednesday.itunes.music.app.source.cache.WatchHistoryEntity
import java.util.*

class MusicRepository {
    private val api = ItunesApi.api
    private val db = ItunesMusicCache.database

    fun getMovies(keyword: String, itemCount: Int = 50): BoundaryBundle<MusicEntity> {
        val musicBoundary = MusicBoundary(keyword, api, db, itemCount)
        val musicFactory = db.musicDao().getMusicsByKeyword(keyword)
        val pageList = LivePagedListBuilder<Int, MusicEntity>(musicFactory, itemCount).apply {
            setBoundaryCallback(musicBoundary)
        }.build()
        return BoundaryBundle(pageList, musicBoundary.itemCountSignal)
    }

    fun getMovie(trackId: String): LiveData<MusicEntity> {
        IO_EXECUTOR.execute {
            db.watchHistoryDao().insert(WatchHistoryEntity(trackId, Date()))
        }
        return db.musicDao().getMusic(trackId)
    }

    fun getWatchHistory() = db.watchHistoryDao().getMusicHistory()
}