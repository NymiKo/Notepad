package com.example.notepad.di

import android.app.Application
import com.example.data.storage.RoomDatabaseApp

class App: Application() {
    companion object {
        lateinit var roomDatabase: RoomDatabaseApp
    }

    override fun onCreate() {
        super.onCreate()
        roomDatabase = RoomDatabaseApp.buildDataSource(context = applicationContext)
    }
}