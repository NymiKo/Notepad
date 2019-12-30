package com.example.data.storage.contract

class RecordSqlContract {

    companion object {
        const val fetch = "SELECT * FROM ${RoomContract.tableRecords}"
    }
}