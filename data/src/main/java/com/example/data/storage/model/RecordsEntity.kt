package com.example.data.storage.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.example.data.storage.contract.RoomContract

@Entity(tableName = RoomContract.tableRecords)
data class RecordsEntity(@PrimaryKey var id_record: Int, val header: String, val content: String, val date: String)