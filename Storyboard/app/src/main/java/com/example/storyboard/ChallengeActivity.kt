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
    }

    override fun onResume() {
        super.onResume()
    }
        private fun initializeUI() {

        worksView = findViewById(R.id.worksView)

        titles = ArrayList()
        titles?.add("5k Words")
        titles?.add("Creative Prompt")

        val adapter = ArrayAdapter(this,
            R.layout.worksview_item,
            R.id.workTitle, titles!!)

        worksView?.adapter = adapter

        worksView?.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->

                val submitIntent = Intent(applicationContext, SubmitChallengeActivity::class.java)
                submitIntent.putExtra("CHALLENGE_NAME" ,view.workTitle.text.toString())
                submitIntent.putExtra(UserID ,intent.getStringExtra(UserID))
                submitIntent.putExtra("WORKS" ,intent.getStringExtra("WORKS"))

                startActivity(submitIntent)}


    }

    companion object {
        val UserID = "com.example.tesla.myhomelibrary.UID"
    }


}