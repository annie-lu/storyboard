package com.example.storyboard

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WorksDashActivity: AppCompatActivity() {

    private var createBtn: Button? = null
    private var editBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_worksdash)

        initializeUI()

        createBtn?.setOnClickListener {

        }

        editBtn?.setOnClickListener {

        }
    }

    private fun initializeUI() {
        createBtn = findViewById(R.id.create) as Button
        editBtn = findViewById(R.id.edit) as Button
    }

}