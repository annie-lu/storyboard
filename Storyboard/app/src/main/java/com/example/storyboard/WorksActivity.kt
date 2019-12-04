package com.example.storyboard

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private var cancelBtn: Button? = null
private var saveBtn: Button? = null
private var worksTitle: EditText? = null
private var worksContent : EditText? = null

private var mDatabaseWorks: DatabaseReference? = null

private var mDatabaseUsers: DatabaseReference? = null
private var mDatabaseTitles: DatabaseReference? = null
private var mDatabase: FirebaseDatabase? = null
private var workid: String = ""

class WorksActivity: AppCompatActivity()
    {

    override fun onCreate(savedInstanceState: Bundle?) {

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseWorks = mDatabase!!.reference!!.child("Works")
        mDatabaseTitles = mDatabase!!.reference!!.child("Titles")


        mDatabaseUsers = mDatabase!!.reference!!.child("Users").child(intent.getStringExtra("CURRUSER"))

        workid = intent.getStringExtra("WORKID")

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_works)

        initializeUI()

        cancelBtn?.setOnClickListener {
            //TODO: HERE WE SHOULD EXIT OUT
            finish()
        }

        saveBtn?.setOnClickListener {
            //TODO: HERE WE SHOULD CREATE THE WORKS OR EDIT THE WORKS
            val id = mDatabaseWorks!!.push().key

            //defunct
            //mDatabaseTitles!!.child(workid!!).child("title").setValue(intent.getStringExtra("TITLE"))

            mDatabaseWorks!!.child(workid!!).child("content").setValue(worksContent!!.text.toString().trim() )
            //mDatabaseWorks!!.child(workid!!).child("title").setValue(intent.getStringExtra("TITLE"))


            mDatabaseUsers!!.child("Works").setValue(intent.getStringExtra("WORKS")+", "+workid.toString().trim() )
            //mDatabaseWorks!!.child(workid!!).child("title").setValue(intent.getStringExtra("TITLE"))

            //setting proper title

            mDatabaseTitles!!.child(workid!!).child("title").setValue(worksTitle!!.text.toString().trim())
            mDatabaseWorks!!.child(workid!!).child("title").setValue(worksTitle!!.text.toString().trim())


            Log.i("HELP", "title: " + worksTitle!!.text.toString())

            true
        }

    }

        private fun initializeUI() {

            worksTitle = findViewById<EditText>(R.id.title)
            worksContent = findViewById<EditText>(R.id.content)
            cancelBtn = findViewById(R.id.cancel) as Button
            saveBtn = findViewById(R.id.save) as Button

            worksTitle?.setText(intent.getStringExtra("TITLE").toString())
        }



        companion object {
            val CHALLENGE_NAME = "com.example.tesla.myhomelibrary.authorname"
            val AUTHOR_ID = "com.example.tesla.myhomelibrary.authorid"
            val UserID = "com.example.tesla.myhomelibrary.UID"
        }
}