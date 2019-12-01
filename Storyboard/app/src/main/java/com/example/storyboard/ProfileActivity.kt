package com.example.storyboard

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference

class ProfileActivity : AppCompatActivity() {

    private var nameTV: TextView? = null
    private var bioTV : TextView? = null
    private var worksView: ListView? = null

    private var titles: MutableList<String>? = null
    private var mDatabase: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        initializeUI()

        titles?.add("owo")
        titles?.add("uwu")
        titles?.add("hello")
        titles?.add("whats up")
        titles?.add("owo1")
        titles?.add("uwu1")
        titles?.add("hello1")
        titles?.add("whats up1")
        titles?.add("owo2")
        titles?.add("uwu2")
        titles?.add("hello2")
        titles?.add("whats up2")
        titles?.add("owo11")
        titles?.add("uwu11")
        titles?.add("hello11")
        titles?.add("whats up11")
        titles?.add("owo111")
        titles?.add("uwu111")
        titles?.add("hello111")
        titles?.add("whats up111")
        titles?.add("owo211")
        titles?.add("uwu211")
        titles?.add("hello211")
        titles?.add("whats up211")
    }

    private fun initializeUI() {
        nameTV = findViewById(R.id.name)
        bioTV = findViewById(R.id.bio)
        worksView = findViewById(R.id.worksView)

        titles = ArrayList()

        val adapter = ArrayAdapter(this,
            R.layout.worksview_item,
            R.id.workTitle, titles!!)

        worksView?.adapter = adapter


    }



}