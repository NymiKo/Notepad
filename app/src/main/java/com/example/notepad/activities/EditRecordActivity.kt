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
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.notepad.R
import com.example.notepad.presenters.EditRecordPresenter
import com.example.notepad.view.EditRecordView
import kotlinx.android.synthetic.main.activity_edit_record.*
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
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.saveRecord -> if(editContent.length() > 0 && (contentCheck != editContent.text.toString()
                        || headerCheck != editHeader.text.toString())) saveRecord()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (editContent.length() != 0 && (contentCheck != editContent.text.toString() || headerCheck != editHeader.text.toString())) {
            val dialogDel = AlertDialog.Builder(this)
            dialogDel.setTitle("Сохранение")
            dialogDel.setMessage("Сохранить запись?")
            dialogDel.setPositiveButton("Да", DialogInterface.OnClickListener() { dialog: DialogInterface, i: Int -> run{
                    saveRecord()
                }
            })
            dialogDel.setNegativeButton("Нет", DialogInterface.OnClickListener(){ dialog: DialogInterface, i: Int -> run{
                    finish()
                }
            })
            dialogDel.show()
        } else {
            super.onBackPressed()
        }
    }

    override fun presentEditor(header: String,content: String) {
        editHeader.visibility = View.VISIBLE
        editContent.visibility = View.VISIBLE
        textLoading.visibility = View.GONE
        editHeader.setText(header)
        editContent.setText(content)
        headerCheck = header
        contentCheck = content
    }

    override fun presentLoading() {
        editHeader.visibility = View.GONE
        editContent.visibility = View.GONE
        textLoading.visibility = View.VISIBLE
    }

    @SuppressLint("SimpleDateFormat", "CommitPrefEdits")
    override fun saveRecord() {
        if(id_record >= 0) {
            if(contentCheck != editContent.text.toString() || headerCheck != editHeader.text.toString()) {
                editRecordPresenter.updateRecord(id_record = id_record, header = editHeader.text.toString(), content = editContent.text.toString(),
                    date = SimpleDateFormat("dd/M/yyyy HH:mm:ss").format(Date()).toString())
                Toast.makeText(applicationContext, "Изменения сохранены!", Toast.LENGTH_SHORT).show()
            }
        } else {
            var count = mSettings!!.getInt("count", 0)
            editRecordPresenter.insertRecords(id_record = count, header = editHeader.text.toString(), content = editContent.text.toString(),
                date = SimpleDateFormat("dd/M/yyyy HH:mm:ss").format(Date()).toString())
            count++
            editor = mSettings!!.edit()
            editor.putInt("count", count)
            editor.apply()
            Toast.makeText(applicationContext, "Сохранено!", Toast.LENGTH_SHORT).show()
        }
        finish()
    }
}
