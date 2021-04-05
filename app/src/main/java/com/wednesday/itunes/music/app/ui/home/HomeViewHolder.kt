package com.wednesday.itunes.music.app.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.wednesday.itunes.music.app.R
import com.wednesday.itunes.music.app.source.cache.MusicEntity
import com.wednesday.itunes.music.app.util.toCurrency
import com.squareup.picasso.Picasso
import com.wednesday.itunes.music.app.databinding.ItemSearchedMusicBinding
import com.wednesday.itunes.music.app.databinding.ItemWatchedMusicBinding

/**
 * Manages how the [ItemSearchedArtistBinding] will look like and its state!
 */
class SearchResultViewHolder(private val binder: ItemSearchedMusicBinding) : RecyclerView.ViewHolder(binder.root) {

    /**
     * Set the movie information to the View.
     */
    fun init(item: MusicEntity) {
        binder.music = item
        binder.apply {
            songTitle.text = item.name
            artistName.text = item.artist
            price.text = item.price.toCurrency(item.currency)
            songShortDesc.text = item.shortDesc
            genre.text = item.genre
            Picasso.get()
                .load(item.image)
                .placeholder(R.drawable.ic_film)
                .centerCrop()
                .fit()
                .into(artistImage)
        }
    }

    /**
     * Remove the artist information from the view.
     */
    fun clear() {
        binder.music = null
        binder.apply {
            artistImage.setImageBitmap(null)
            songTitle.text = null
            artistName.text = null
            price.text = null
            songShortDesc.text = null
            genre.text = null
        }
    }
}

/**
 * Manages how the [ItemWatchedArtistBinding] will look like and its state!
 */
class WatchResultViewHolder(val binder: ItemWatchedMusicBinding) : RecyclerView.ViewHolder(binder.root)