package com.wednesday.itunes.music.app.source.cache.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wednesday.itunes.music.app.source.cache.MusicEntity
import com.wednesday.itunes.music.app.source.cache.SearchResultsEntity

@Dao
abstract class MusicDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertMusic(music: MusicEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertMusics(musics: List<MusicEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertSearches(genres: List<SearchResultsEntity>)

    @Query("SELECT musics.* FROM musics INNER JOIN search_results ON musics.track_id = search_results.track_id WHERE search_results.keyword = :keyword")
    abstract fun getMusicsByKeyword(keyword: String): DataSource.Factory<Int, MusicEntity>

    @Query("SELECT * FROM musics WHERE track_id = :trackId")
    abstract fun getMusic(trackId: String): LiveData<MusicEntity>
}