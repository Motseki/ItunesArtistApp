package com.wednesday.itunes.music.app.source.cache.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wednesday.itunes.music.app.source.cache.MusicEntity
import com.wednesday.itunes.music.app.source.cache.WatchHistoryEntity

@Dao
abstract class WatchHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(music: WatchHistoryEntity)

    @Query("SELECT musics.* FROM musics INNER JOIN watch_history ON musics.track_id = watch_history.track_id ORDER BY watch_history.last_watch DESC")
    abstract fun getMusicHistory(): LiveData<List<MusicEntity>>
}