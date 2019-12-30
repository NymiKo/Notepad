package com.example.notepad.view

import com.arellomobile.mvp.MvpView
import com.example.domain.models.Records

interface RecordListView: MvpView {
    fun presentRecords(data: List<Records>)
}