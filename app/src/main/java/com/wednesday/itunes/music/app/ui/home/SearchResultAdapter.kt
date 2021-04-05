package com.wednesday.itunes.music.app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.wednesday.itunes.music.app.databinding.ItemSearchedMusicBinding
import com.wednesday.itunes.music.app.source.cache.MusicEntity
import com.wednesday.itunes.music.app.util.toCurrency
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_searched_music_details.view.*
import java.text.NumberFormat

/**
 * It arranges the [ArtistEntity] in the [androidx.recyclerview.widget.RecyclerView].
 * This operation will occur in the background thread.
 */
private object DifferSearchMovie : DiffUtil.ItemCallback<MusicEntity>() {
    override fun areItemsTheSame(oldItem: MusicEntity, newItem: MusicEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MusicEntity, newItem: MusicEntity): Boolean {
        return oldItem == newItem
    }
}

/**
 * The [androidx.recyclerview.widget.RecyclerView.Adapter] for artist.
 */
class SearchedMoviesAdapter(private val viewModel: HomeViewModel) :
    PagedListAdapter<MusicEntity, SearchResultViewHolder>(DifferSearchMovie) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemSearchedMusicBinding.inflate(inflater, parent, false)
        view.parent.setOnClickListener {
            view.music?.let {
                viewModel.openMovie(it.id)
            }
        }
        return SearchResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val item = getItem(position)
        // When null it means SearchResultViewHolder is a Placeholder
        if (item != null) {
            holder.init(item)
        } else {
            holder.clear()
        }
    }

}