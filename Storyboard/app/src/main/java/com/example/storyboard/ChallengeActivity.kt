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
import kotlinx.android.synthetic.main.worksview_item.view.*


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
                Log.i("trying this out", adapterView.workTitle.text.toString())
                Log.i("trying this out", view.toString())
                Log.i("trying this out", l.toString())

                val submitIntent = Intent(applicationContext, SubmitChallengeActivity::class.java)
                submitIntent.putExtra(CHALLENGE_NAME ,adapterView.workTitle.text.toString())
                submitIntent.putExtra(UserID ,intent.getStringExtra(UserID))
                submitIntent.putExtra("WORKS" ,intent.getStringExtra("WORKS"))

                //intent.putExtra(USER_ID, FirebaseUser.getCurrentUser())
                //intent.putExtra(AUTHOR_ID, author.authorId)
                //intent.putExtra(AUTHOR_NAME, author.authorName)
                startActivity(submitIntent)
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

    companion object {
        val UserID = "com.example.tesla.myhomelibrary.UID"
        val CHALLENGE_NAME = "com.example.tesla.myhomelibrary.authorname"
        val AUTHOR_ID = "com.example.tesla.myhomelibrary.authorid"
    }


}