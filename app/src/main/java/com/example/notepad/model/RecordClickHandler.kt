package com.example.notepad.model

import com.example.domain.models.Records

interface RecordClickHandler {
fun onItemClick(item: Records, id_record: Int?, header: String, content: String, date: String)
}