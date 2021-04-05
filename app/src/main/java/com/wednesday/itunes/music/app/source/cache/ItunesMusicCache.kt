package com.wednesday.itunes.music.app.source.cache

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wednesday.itunes.music.app.AppApplication
import com.wednesday.itunes.music.app.source.cache.dao.MusicDao
import com.wednesday.itunes.music.app.source.cache.dao.WatchHistoryDao

@Database(
    entities = [MusicEntity::class,
        SearchResultsEntity::class,
        WatchHistoryEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    value = [
        DateConverter::class,
        UriConverter::class
    ]
)
abstract class ItunesMusicCache : RoomDatabase() {

    abstract fun musicDao(): MusicDao

    abstract fun watchHistoryDao(): WatchHistoryDao

    companion object {
        const val DATABASE_NAME = "ItunesMusicCache"

        val database = Room.databaseBuilder(
            AppApplication.globalContext,
            ItunesMusicCache::class.java,
            DATABASE_NAME
        ).build()
    }
}
