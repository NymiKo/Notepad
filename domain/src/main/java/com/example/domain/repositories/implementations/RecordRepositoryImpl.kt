package com.example.domain.repositories.implementations

import com.example.data.storage.RoomDatabaseApp
import com.example.data.storage.model.RecordsEntity
import com.example.domain.converter.RecordConverterImpl
import com.example.domain.models.Records
import com.example.domain.repositories.RecordRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.lang.Exception

class RecordRepositoryImpl(val roomDatabase: RoomDatabaseApp): RecordRepository {
    val recordConverter = RecordConverterImpl()

    override suspend fun fetchRecords(): Deferred<List<Records>> {
        try {
            return GlobalScope.async {
                roomDatabase.recordDao().fetchRecord().map {
                    recordConverter.fromDBToDomain(recordsEntity = it)
                }
            }
        } catch (e: Exception) {
            return GlobalScope.async { error(e) }
        }
    }

    override suspend fun insertRecords(id_record: Int, header: String, content: String, date: String) {
        try {
            val insertRecord = RecordsEntity(id_record = id_record, header = header, content = content, date = date)
            roomDatabase.recordDao().insertRecord(recordEntity = insertRecord)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun deleteRecords(id_record: Int) {
        try {
            val deleteRecord = RecordsEntity(id_record = id_record, header = "", content = "", date = "")
            roomDatabase.recordDao().deleteRecord(recordEntity = deleteRecord)
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    @Suppress("UNREACHABLE_CODE")
    override suspend fun getRecord(id_record: Int): RecordsEntity {
        try {
            return roomDatabase.recordDao().getRecord(id = id_record)
        } catch(e: Exception) {
            return error(e)
        }
    }

    override suspend fun updateRecord(id_record: Int, header: String, content: String, date: String) {
        try {
            val updateRecord = RecordsEntity(id_record = id_record, header = header, content = content, date = date)
            roomDatabase.recordDao().updateRecord(recordEntity = updateRecord)
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }
}