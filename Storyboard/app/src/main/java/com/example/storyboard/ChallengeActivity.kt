package com.example.storyboard

import android.content.Intent
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


class ChallengeActivity : AppCompatActivity() {

    private var worksView: ListView? = null


    private var titles: MutableList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge)

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

        worksView?.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->

                val intent = Intent(applicationContext, SubmitChallengeActivity::class.java)

                //intent.putExtra(USER_ID, FirebaseUser.getCurrentUser())
                //intent.putExtra(AUTHOR_ID, author.authorId)
                //intent.putExtra(AUTHOR_NAME, author.authorName)
                startActivity(intent)
            }

/*
        if(findViewById<View>(R.id.spinnerCountry) == null) {
            Log.i("help","this is null")
        }else{
            Log.i("help","this is NOT null")
        }
        spinnerCountry = findViewById<View>(R.id.spinnerCountry) as Spinner
  */
    }



}