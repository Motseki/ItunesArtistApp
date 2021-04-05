package com.wednesday.itunes.music.app.source.cache

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "musics")
data class MusicEntity(
    @PrimaryKey @ColumnInfo(name = "track_id") var id: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "price") var price: Double,
    @ColumnInfo(name = "currency") var currency: String,
    @ColumnInfo(name = "short_desc") var shortDesc: String?,
    @ColumnInfo(name = "long_desc") var longDesc: String?,
    @ColumnInfo(name = "release_date") var releaseDate: Date,
    @ColumnInfo(name = "genre") var genre: String,
    @ColumnInfo(name = "artist") var artist: String,
    @ColumnInfo(name = "preview") var preview: Uri?,
    @ColumnInfo(name = "image") var image: Uri?
)

@Entity(
    tableName = "watch_history",
    foreignKeys = [ForeignKey(
        entity = MusicEntity::class,
        parentColumns = ["track_id"],
        childColumns = ["track_id"]
    )]
)
data class WatchHistoryEntity(
    @PrimaryKey @ColumnInfo(name = "track_id") var trackId: String,
    @ColumnInfo(name = "last_watch") var lastWatch: Date
)

@Entity(
    tableName = "search_results",
    primaryKeys = ["track_id", "keyword"],
    foreignKeys = [ForeignKey(
        entity = MusicEntity::class,
        parentColumns = ["track_id"],
        childColumns = ["track_id"]
    )]
)
data class SearchResultsEntity(
    @ColumnInfo(name = "keyword") var keyword: String,
    @ColumnInfo(name = "track_id") var trackId: String
)