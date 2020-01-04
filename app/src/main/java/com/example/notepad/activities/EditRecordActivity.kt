package com.example.notepad.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.*
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.notepad.R
import com.example.notepad.presenters.EditRecordPresenter
import com.example.notepad.view.EditRecordView
import kotlinx.android.synthetic.main.activity_edit_record.*
import kotlinx.android.synthetic.main.cell_record.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class EditRecordActivity : MvpAppCompatActivity(), EditRecordView {

    @InjectPresenter
    lateinit var editRecordPresenter: EditRecordPresenter

    val APP_PREFERENCES = "Settings"
    var mSettings: SharedPreferences? = null
    lateinit var editor: SharedPreferences.Editor
    private var id_record = -2
    private var headerCheck = ""
    private var contentCheck = ""
    private var checked_settings = true

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_record)
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        val bundle = intent.extras
        if(bundle != null) {
            id_record = intent.extras.getInt("id_record")
        }

        editRecordPresenter.getRecord(id_record = id_record)

        checked_settings = mSettings!!.getBoolean("dialog_save", false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.saveRecord -> saveRecord()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        when(checked_settings){
            true -> saveRecord()
            false -> {
                AlertDialog.Builder(this)
                    .setTitle("Сохранение")
                    .setMessage("Сохранить запись?")
                    .setPositiveButton("Да") { dialog, which ->
                        saveRecord()
                    }
                    .setNegativeButton("Нет") { dialog, which ->
                        super.onBackPressed()
                    }
                    .create()
                    .show()
            }
        }
    }

    override fun presentEditor(header: String,content: String) {
        editHeader.visibility = VISIBLE
        editContent.visibility = VISIBLE
        progressBar.visibility = GONE
        editHeader.setText(header)
        editContent.setText(content)
        headerCheck = header
        contentCheck = content
    }

    override fun presentLoading() {
        editHeader.visibility = GONE
        editContent.visibility = GONE
        progressBar.visibility = VISIBLE
    }

    @SuppressLint("SimpleDateFormat", "CommitPrefEdits")
    override fun saveRecord() {
        if(id_record >= 0) {
            editRecordPresenter.updateRecord(id_record = id_record, header = editHeader.text.toString(), content = editContent.text.toString(),
                date = SimpleDateFormat("dd/M/yyyy HH:mm:ss").format(Date()).toString(), contentCheck = contentCheck, headerCheck = headerCheck)
        } else {
            var count = mSettings!!.getInt("count", 0)
            editRecordPresenter.insertRecords(id_record = count, header = editHeader.text.toString(), content = editContent.text.toString(),
                date = SimpleDateFormat("dd/M/yyyy HH:mm:ss").format(Date()).toString(), contentLenght = editContent.text.length)
            count++
            editor = mSettings!!.edit()
            editor.putInt("count", count)
            editor.apply()
        }
        finish()
    }

    override fun showSuccessUpdateRecord() {
        Toast.makeText(applicationContext, "Изменения сохранены!", Toast.LENGTH_SHORT).show()
    }

    override fun showSuccessInsertRecord() {
        Toast.makeText(applicationContext, "Сохранено!", Toast.LENGTH_SHORT).show()
    }
}
