package com.example.notepad.di

import android.app.Application
import android.support.multidex.MultiDexApplication
import com.example.data.storage.RoomDatabaseApp

class App: MultiDexApplication() {
    companion object {
        lateinit var roomDatabase: RoomDatabaseApp
    }

    override fun onCreate() {
        super.onCreate()
        roomDatabase = RoomDatabaseApp.buildDataSource(context = applicationContext)
    }
}