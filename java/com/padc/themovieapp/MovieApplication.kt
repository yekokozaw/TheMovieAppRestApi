package com.padc.themovieapp

import android.app.Application
import com.padc.themovieapp.data.models.MovieModelImpl

//first running class when application is start
class MovieApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        MovieModelImpl.initDatabase(applicationContext)
    }
}