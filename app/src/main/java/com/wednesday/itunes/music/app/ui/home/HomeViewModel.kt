package com.wednesday.itunes.music.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.wednesday.itunes.music.app.source.repository.MusicRepository
import com.wednesday.itunes.music.app.util.SingleLiveEvent

class HomeViewModel : ViewModel() {
    private val repository = MusicRepository()
    private val search = MutableLiveData<String>()
    private val selectedMusic = MutableLiveData<String>()

    private val searchBundle = Transformations.map(search) {
        if (it.isEmpty()) {
            repository.getMovies("star")
        } else {
            repository.getMovies(it)
        }
    }

   val searchedMusics = Transformations.switchMap(searchBundle) { it.boundary }
   val watchedMusics = repository.getWatchHistory()
   val viewedMusic = Transformations.switchMap(selectedMusic) { repository.getMovie(it) }

    private val _openFragment = SingleLiveEvent<Void>()
    val openFragment: LiveData<Void>
        get() {
            return _openFragment
        }

    fun search(keyword: String) {
        if (search.value != keyword) search.value = keyword
    }

    fun openMovie(movieId: String) {
        _openFragment.call()
        selectedMusic.value = movieId
    }

    fun setCount(count: Int) {
        searchBundle.value?.itemCount?.invoke(count)
    }
}