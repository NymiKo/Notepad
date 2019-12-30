package com.example.notepad.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.domain.models.Records
import com.example.notepad.R
import com.example.notepad.adapters.interface_adapter.ItemTouchHelperAdapter
import com.example.notepad.model.RecordClickHandler

class RecordAdapter: RecyclerView.Adapter<RecordAdapter.ViewHolder>(), ItemTouchHelperAdapter {

    private val mRecordList: MutableList<Records> = ArrayList()
    private var recordClickHandler: RecordClickHandler? = null

    fun setData(newRecord: List<Records>) {
        mRecordList.clear()
        mRecordList.addAll(newRecord)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.cell_record, viewGroup, false),
            recordClickHandler = recordClickHandler)
    }

    override fun getItemCount(): Int {
        return mRecordList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(model = mRecordList[position])
    }

    fun attachClickHandler(recordClickHandler: RecordClickHandler) {
        this.recordClickHandler = recordClickHandler
    }

    class ViewHolder(itemView: View, private val recordClickHandler: RecordClickHandler?): RecyclerView.ViewHolder(itemView) {
        private val textHeader: TextView = itemView.findViewById(R.id.textHeader)
        private val textContent: TextView = itemView.findViewById(R.id.textContent)
        private val textDate: TextView = itemView.findViewById(R.id.textDate)
        private val cellRecord = itemView.findViewById<LinearLayout>(R.id.llCellRecord)

        fun bind(model: Records) {
            textHeader.text = model.header
            textContent.text = model.content
            textDate.text = model.date

            cellRecord.setOnClickListener {
                recordClickHandler?.onItemClick(item = model, id_record = model.id_record, header = model.header, content = model.content, date = model.date)
            }
        }
    }

    override fun onItemDismiss(position: Int) {
        mRecordList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun restoreItem(item: Records, position: Int) {
        mRecordList.add(position, item)
        notifyItemInserted(position)
    }

    fun getItem(position: Int): Records {
        return mRecordList[position]
    }

    fun getId(position: Int): Int {
        return mRecordList[position].id_record!!
    }
}