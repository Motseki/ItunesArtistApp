package com.wednesday.itunes.music.app.source.boundary

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class BoundaryBundle<T>(
    val boundary: LiveData<PagedList<T>>,
    val itemCount: (Int) -> Unit
)