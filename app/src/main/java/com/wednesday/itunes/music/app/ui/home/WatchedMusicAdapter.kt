package com.wednesday.itunes.music.app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wednesday.itunes.music.app.databinding.ItemWatchedMusicBinding
import com.wednesday.itunes.music.app.source.cache.MusicEntity
import com.squareup.picasso.Picasso

/**
 * The [androidx.recyclerview.widget.RecyclerView.Adapter] for artist.
 */
class WatchedMusicAdapter(private val viewModel: HomeViewModel) : RecyclerView.Adapter<WatchResultViewHolder>() {

    var musics: List<MusicEntity>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = musics?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemWatchedMusicBinding.inflate(inflater, parent, false)
        view.parent.setOnClickListener {
            view.music?.let {
                viewModel.openMovie(it.id)
            }
        }
        return WatchResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: WatchResultViewHolder, position: Int) {
        val item = musics?.get(position)!!
        holder.binder.apply {
            music = item
            songTitle.text = item.name
            artistName.text = item.artist
            Picasso.get()
                .load(item.image)
                .centerCrop()
                .fit()
                .into(artistImage)
        }
    }

}