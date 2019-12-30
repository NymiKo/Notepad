package com.example.domain.converter

import com.example.data.storage.model.RecordsEntity
import com.example.domain.models.Records

interface RecordConverter {
    fun fromDBToDomain(recordsEntity: RecordsEntity): Records
}