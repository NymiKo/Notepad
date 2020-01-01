package com.example.notepad.fragments


import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.domain.models.Records
import com.example.notepad.R
import com.example.notepad.activities.EditRecordActivity
import com.example.notepad.adapters.RecordAdapter
import com.example.notepad.model.RecordClickHandler
import com.example.notepad.presenters.RecordListPresenter
import com.example.notepad.view.RecordListView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_edit_record.*
import kotlinx.android.synthetic.main.fragment_record_list.*


class RecordListFragment : MvpAppCompatFragment(), RecordListView{

    private val mAdapter = RecordAdapter()
    private lateinit var mItemTouchHelper: ItemTouchHelper

    @InjectPresenter
    lateinit var recordListPresenter: RecordListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAdapter.attachClickHandler(object : RecordClickHandler{
            override fun onItemClick(item: Records, id_record: Int?, header: String, content: String, date: String) {
                val intent = Intent(context, EditRecordActivity::class.java)
                if (id_record != null) {
                    intent.putExtra("id_record", id_record)
                }
                startActivity(intent)
            }
        })

        MobileAds.initialize(this.activity, "ca-app-pub-9598699328338820~5648342881")
    }

    @Suppress("UNREACHABLE_CODE")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ad = AdRequest.Builder()
            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            .build()
        adViewBanner.loadAd(ad)

        setupAdapter()

        btnCreate.setOnClickListener {
            val intent = Intent(context, EditRecordActivity::class.java)
            startActivity(intent)
        }

        val adRequest = AdRequest.Builder()
            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            .build()
        adViewBanner.loadAd(adRequest)
    }

    override fun onStart() {
        super.onStart()
        recordListPresenter.fetchRecords()
    }

    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.VERTICAL, false)
        recycleRecordList.layoutManager = layoutManager
        recycleRecordList.adapter = mAdapter

        deleteRecord()
    }

    override fun presentRecords(data: List<Records>) {
        mAdapter.setData(newRecord = data)
    }

    fun deleteRecord() {
        val callback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START or ItemTouchHelper.END) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val dialogDel = AlertDialog.Builder(context)
                dialogDel.setTitle("УДАЛЕНИЕ")
                dialogDel.setMessage("Удалить запись?")
                dialogDel.setPositiveButton("Да", DialogInterface.OnClickListener() { dialog: DialogInterface, i: Int -> run{
                        val id: Int? = mAdapter.getId(viewHolder.adapterPosition)
                        Log.e("Error", id.toString())
                        mAdapter.onItemDismiss(viewHolder.adapterPosition)
                        recordListPresenter.deleteRecord(id_record = id!!)
                        Toast.makeText(activity?.applicationContext, "Запись удалена!", Toast.LENGTH_SHORT).show()
                    }
                })
                dialogDel.setNegativeButton("Нет", DialogInterface.OnClickListener(){ dialog: DialogInterface, i: Int -> run{
                        val item = mAdapter.getItem(viewHolder.adapterPosition)
                        mAdapter.restoreItem(item, viewHolder.adapterPosition)
                        mAdapter.onItemDismiss(viewHolder.adapterPosition)
                        dialog.cancel()
                    }
                })
                dialogDel.show()
            }
        }
        mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper.attachToRecyclerView(recycleRecordList)
    }
}
