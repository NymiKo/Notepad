package com.example.data.storage

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.data.storage.contract.RoomContract
import com.example.data.storage.dao.RecordDao
import com.example.data.storage.model.RecordsEntity

@Database(entities = [RecordsEntity::class], version = 1)
abstract class RoomDatabaseApp: RoomDatabase() {

    abstract fun recordDao(): RecordDao

    companion object {

        fun buildDataSource(context: Context): RoomDatabaseApp = Room.databaseBuilder(
            context, RoomDatabaseApp::class.java, RoomContract.databaseApp
        ).build()
    }
}