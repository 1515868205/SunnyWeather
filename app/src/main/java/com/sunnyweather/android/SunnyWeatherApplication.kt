package com.sunnyweather.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyWeatherApplication : Application(){
    companion object{
        const val Token = "00VvLxGERG1asxHL"
        @SuppressLint("StaticFieldLeak")
        lateinit var  context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}