package com.wednesday.itunes.music.app.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.wednesday.itunes.music.app.R
import com.wednesday.itunes.music.app.databinding.FragmentMusicDetailsBinding
import com.wednesday.itunes.music.app.ui.MainActivity
import com.wednesday.itunes.music.app.util.toCurrency
import com.squareup.picasso.Picasso


/**
 * This is the screen that shows the entire information about the artist.
 */
class MusicDetailsFragment : Fragment() {

    private lateinit var binder: FragmentMusicDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_music_details, container, false)

        /**
         * Add a 60% gap in between our price tag and movie description!
         */
        val mHeight = activity!!.windowManager.defaultDisplay.height
        binder.verticalSpan.layoutParams.height = (mHeight * 0.6).toInt()

        return binder.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = activity as MainActivity
        val viewModel = activity.homeViewModel
        viewModel.viewedMusic.observe(this, Observer { entity ->
            if (entity != null) {
                binder.apply {
                    movieTitle.text = entity.name
                    movieActor.text = entity.artist
                    movieDesc.text = entity.longDesc
                    moviePrice.text = entity.price.toCurrency(entity.currency)
                    movieActor.text = entity.artist
                    movieGenre.text = entity.genre
                    Picasso.get()
                        .load(entity.image)
                        .centerCrop()
                        .fit()
                        .into(movieImage)

                    playVideo.setOnClickListener {
                        if (entity.preview != null) {
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.setDataAndType(entity.preview, "video/*")
                            startActivity(intent)
                        }
                    }

                    movieTitle.paint
                }
            }
        })
    }

}