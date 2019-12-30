package com.example.notepad.view

import com.arellomobile.mvp.MvpView

interface EditRecordView: MvpView {
    fun presentEditor(header: String, content: String)
    fun presentLoading()
    fun saveRecord()
}