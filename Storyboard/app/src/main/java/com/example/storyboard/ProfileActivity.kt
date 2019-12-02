package com.example.storyboard

import android.graphics.Color
import android.graphics.PorterDuff
import android.media.Image
import android.os.Bundle
import android.os.PersistableBundle
import android.text.InputType
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference


class ProfileActivity : AppCompatActivity() {

    private var nameTV: EditText? = null
    private var bioTV : EditText? = null
    private var editButton : ImageButton? = null
    private var worksView: ListView? = null

    private var titles: MutableList<String>? = null
    private var mDatabase: DatabaseReference? = null

    private var editing = false

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


        //nameTV?.inputType = InputType.TYPE_NULL
        //nameTV?.isEnabled = false
        //nameTV?.isFocusable = false
        //nameTV?.isClickable = true

        // TODO - MAKE IT SO ONLY YOU CAN EDIT OWN PROFILE, GET USERID FROM EXTRA
        editButton?.setOnClickListener {
            editing = !editing

            if (editing) {
                (it as ImageButton).setColorFilter(Color.parseColor("#00BCD4"))

            }
            else {
                (it as ImageButton).setColorFilter(android.R.color.transparent)
            }

            Toast.makeText(this, "Click button detected", Toast.LENGTH_SHORT).show()
            nameTV?.isEnabled = !(nameTV!!.isEnabled)
            bioTV?.isEnabled = !(bioTV!!.isEnabled)
        }


        //NOT WORKING
        nameTV?.setOnClickListener {
            Toast.makeText(this, "Long click detected", Toast.LENGTH_SHORT).show()
            nameTV?.isEnabled = true
            Log.i("HELP", "name clicked")
            true
        }

        bioTV?.setOnLongClickListener {
            Toast.makeText(this, "Long click detected", Toast.LENGTH_SHORT).show()
            bioTV?.isEnabled = true
            Log.i("HELP", "bio clicked")

            true
        }
    }

    private fun initializeUI() {
        nameTV = findViewById<EditText>(R.id.name)
        bioTV = findViewById<EditText>(R.id.bio)
        editButton = findViewById<ImageButton>(R.id.editButton)
        worksView = findViewById(R.id.worksView)

        titles = ArrayList()

        val adapter = ArrayAdapter(this,
            R.layout.worksview_item,
            R.id.workTitle, titles!!)

        worksView?.adapter = adapter

        nameTV?.isEnabled = false
        bioTV?.isEnabled = false


    }



}