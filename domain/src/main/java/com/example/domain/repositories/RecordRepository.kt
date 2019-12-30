package com.example.domain.repositories

import com.example.data.storage.model.RecordsEntity
import com.example.domain.models.Records
import kotlinx.coroutines.Deferred
import java.lang.Exception

interface RecordRepository {
    suspend fun fetchRecords(): Deferred<List<Records>>
    suspend fun insertRecords(id_record: Int, header: String, content: String, date: String)
    suspend fun deleteRecords(id_record: Int)
    suspend fun getRecord(id_record: Int): RecordsEntity
    suspend fun updateRecord(id_record: Int, header: String, content: String, date: String)
}