package com.example.notepad.fragments


import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.notepad.R
import kotlinx.android.synthetic.main.fragment_settings_app.*

class SettingsApp : Fragment() {

    val APP_PREFERENCES = "Settings"
    var mSettings: SharedPreferences? = null
    lateinit var editor: SharedPreferences.Editor
    var dialog_save = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSettings = activity?.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    }

    @Suppress("UNREACHABLE_CODE")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings_app, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog_save = mSettings!!.getBoolean("dialog_save", false)
        switch_save_dialog.isChecked = dialog_save
    }

    @SuppressLint("CommitPrefEdits")
    override fun onStop() {
        if(dialog_save != switch_save_dialog.isChecked){
            editor = mSettings!!.edit()
            editor.putBoolean("dialog_save", switch_save_dialog.isChecked)
            editor.apply()
        }
        super.onStop()
    }
}
