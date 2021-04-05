package com.wednesday.itunes.music.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wednesday.itunes.music.app.R
import com.wednesday.itunes.music.app.databinding.FragmentHomeBinding
import com.wednesday.itunes.music.app.ui.MainActivity

/**
 * A Fragment that shows recently viewed artist and searched artist.
 */
class HomeFragment : Fragment() {


    private lateinit var binder: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binder = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )
        return binder.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = activity as MainActivity
        val viewModel = activity.homeViewModel

        val watchedMusicsAdapter = WatchedMusicAdapter(viewModel)
        val searchedMusicsAdapter = SearchedMoviesAdapter(viewModel)
        binder.watchedMusics.apply {
            adapter = watchedMusicsAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        binder.searchedMusics.apply {
            adapter = searchedMusicsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.search("star")
        viewModel.watchedMusics.observe(this, Observer {
            if (it.isEmpty()) {
                binder.recentLabel.visibility = View.GONE
                binder.watchedMusics.visibility = View.GONE
            } else {
                binder.recentLabel.visibility = View.VISIBLE
                binder.watchedMusics.visibility = View.VISIBLE
            }

            watchedMusicsAdapter.musics = it
        })
        viewModel.searchedMusics.observe(this, Observer {
            viewModel.setCount(it.size)
            searchedMusicsAdapter.submitList(it)
        })

        binder.searchBox.setOnEditorActionListener { _, actionId, _ ->
            val keyword = binder.searchBox.text.toString()
            if (actionId == EditorInfo.IME_ACTION_DONE && keyword.isNotEmpty()) {
                viewModel.search(keyword)
                true
            }
            false
        }
    }
}