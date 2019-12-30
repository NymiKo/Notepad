package com.example.notepad.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.domain.repositories.implementations.RecordRepositoryImpl
import com.example.notepad.di.App
import com.example.notepad.view.EditRecordView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

@InjectViewState
class EditRecordPresenter: MvpPresenter<EditRecordView>() {

    private var headerCheck = ""
    private var contentCheck = ""

    fun insertRecords(id_record: Int, header: String, content: String, date: String) {
        val recordRepository = RecordRepositoryImpl(roomDatabase = App.roomDatabase)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                withContext(Dispatchers.Main) {
                    viewState.presentLoading()
                }
                recordRepository.insertRecords(id_record = id_record,
                    header = if (header == "") {"Без заголовка"}
                            else {header},
                    content = content, date = date)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getRecord(id_record: Int) {
        if(id_record >= 0) {
            val recordRepository = RecordRepositoryImpl(roomDatabase = App.roomDatabase)

            viewState.presentLoading()
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val record = recordRepository.getRecord(id_record = id_record)
                    withContext(Dispatchers.Main) {
                        viewState.presentEditor(header = record.header, content = record.content)
                        contentCheck = record.content
                        headerCheck = record.header
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun updateRecord(id_record: Int, header: String, content: String, date: String) {
        val recordRepository = RecordRepositoryImpl(roomDatabase = App.roomDatabase)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                withContext(Dispatchers.Main) {
                    viewState.presentLoading()
                }
                recordRepository.updateRecord(id_record = id_record,
                    header = if (header == "") {"Без заголовка"}
                            else {header},
                    content = content, date = date)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}