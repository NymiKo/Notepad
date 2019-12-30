package com.example.notepad.adapters.interface_adapter

import com.example.domain.models.Records

interface ItemTouchHelperAdapter {
    fun onItemDismiss(position: Int)
    fun restoreItem(item: Records, position: Int)
}