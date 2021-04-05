package com.wednesday.itunes.music.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.transaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.wednesday.itunes.music.app.R
import com.wednesday.itunes.music.app.databinding.ActivityMainBinding
import com.wednesday.itunes.music.app.ui.detail.MusicDetailsFragment
import com.wednesday.itunes.music.app.ui.home.HomeViewModel

class MainActivity : AppCompatActivity() {

    val homeViewModel by lazy {
        ViewModelProviders.of(this)[HomeViewModel::class.java]
    }

    lateinit var binder: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.setContentView(this, R.layout.activity_main)

        homeViewModel.openFragment.observe(this, Observer {
            supportFragmentManager.transaction {

                add(R.id.container, MusicDetailsFragment())
                addToBackStack(null)
            }
        })
    }

}
