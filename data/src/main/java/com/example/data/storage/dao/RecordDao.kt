package com.example.data.storage.dao

import android.arch.persistence.room.*
import com.example.data.storage.contract.RoomContract
import com.example.data.storage.model.RecordsEntity

@Dao
interface RecordDao {

    @Query("SELECT * FROM ${RoomContract.tableRecords}")
    fun fetchRecord(): List<RecordsEntity>

    @Query("SELECT * FROM ${RoomContract.tableRecords} WHERE id_record = :id")
    fun getRecord(id: Int): RecordsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(recordEntity: RecordsEntity)

    @Update
    fun updateRecord(recordEntity: RecordsEntity)

    @Delete
    fun deleteRecord(recordEntity: RecordsEntity)
}