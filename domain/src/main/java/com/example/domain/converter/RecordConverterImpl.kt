package com.example.domain.converter

import com.example.data.storage.model.RecordsEntity
import com.example.domain.models.Records

class RecordConverterImpl: RecordConverter {

    override fun fromDBToDomain(recordsEntity: RecordsEntity): Records {
        return Records(id_record = recordsEntity.id_record, header = recordsEntity.header, content = recordsEntity.content, date = recordsEntity.date)
    }
}