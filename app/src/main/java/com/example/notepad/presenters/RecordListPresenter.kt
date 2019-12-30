package com.example.notepad.presenters

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.domain.repositories.implementations.RecordRepositoryImpl
import com.example.notepad.di.App.Companion.roomDatabase
import com.example.notepad.view.RecordListView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

@InjectViewState
class RecordListPresenter: MvpPresenter<RecordListView>() {
    val recordRepository = RecordRepositoryImpl(roomDatabase = roomDatabase)

    fun fetchRecords() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val records = recordRepository.fetchRecords().await()
                withContext(Dispatchers.Main) {
                    viewState.presentRecords(data = records)
                    Log.e("CHECK", "Yes")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteRecord(id_record: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                recordRepository.deleteRecords(id_record = id_record)
            } catch (e: Exception) {
                error(e)
            }
        }
    }
}