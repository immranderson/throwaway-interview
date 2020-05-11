package com.example.hsexercise

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        //Under normal circumstances initialize dagger2 here at the top of our dependency graph
    }

}