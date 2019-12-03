package com.example.storyboard

import android.graphics.Color
import android.graphics.PorterDuff
import android.media.Image
import android.os.Bundle
import android.os.PersistableBundle
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference


class SubmitChallengeActivity : AppCompatActivity() {

    private var worksView: ListView? = null


    private var titles: MutableList<String>? = null

    internal var spinnerCountry: Spinner ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.submit_challenge)

        initializeUI()

        titles?.add("owo")
        titles?.add("uwu")



    }

    private fun initializeUI() {

        worksView = findViewById(R.id.worksView)

        titles = ArrayList()

        val adapter = ArrayAdapter(this,
            R.layout.worksview_item,
            R.id.workTitle, titles!!)

        worksView?.adapter = adapter


        if(findViewById<View>(R.id.spinnerCountry) == null) {
            Log.i("help","this is null")
        }else{
            Log.i("help","this is NOT null")
        }
        spinnerCountry = findViewById<View>(R.id.spinnerCountry) as Spinner

    }



}