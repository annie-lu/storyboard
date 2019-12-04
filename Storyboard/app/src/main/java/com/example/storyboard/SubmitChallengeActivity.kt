package com.example.storyboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog


class SubmitChallengeActivity : AppCompatActivity() {

    private var worksView: ListView? = null
    private var promptView: TextView? = null

    private var titles: MutableList<String>? = null
    private var submissions: MutableList<String>? = null

    private var spinner: Spinner ?= null

    private var progressBar: ProgressBar? = null
    private var submitBtn: Button? = null
    private var mDatabaseChallenges: DatabaseReference? = null

    private var mDatabaseTitles: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null

    private var works: List<String>? = null

    private var selectedWork: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.submit_challenge)


        works = intent.getStringExtra("WORKS").split(", ")

        mDatabase = FirebaseDatabase.getInstance()

        mDatabaseChallenges= mDatabase!!.reference!!.child("Challenges")
        mDatabaseTitles = mDatabase!!.reference!!.child("Titles")
        titles = ArrayList()
        mDatabaseTitles!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(w in works!!){
                    titles?.add(dataSnapshot.child(w).child("title").value.toString())
                }
            } override fun onCancelled(databaseError: DatabaseError) {
            }
        })

        mDatabaseChallenges!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                promptView!!.setText("Submit your work to complete this challenge! We add the challenge description here! " +dataSnapshot.child((intent.getStringExtra("CHALLENGE_NAME"))).child("Prompt").value)
            } override fun onCancelled(databaseError: DatabaseError) {
            }
        })


        submissions = ArrayList()
        submissions?.add("Testing")
        submissions?.add("Donuts")


        initializeUI()
        submitBtn!!.setOnClickListener { submitDialog() }


    }

    private fun initializeUI() {

        promptView = findViewById(R.id.bio)

        val adapter = ArrayAdapter(this,
            R.layout.worksview_item,
            R.id.workTitle, submissions!!)

        worksView = findViewById(R.id.worksView)
        worksView?.adapter = adapter

        submitBtn = findViewById(R.id.submitButton)

        progressBar = findViewById(R.id.progressBar)

    }

    private fun submitDialog(){

            val dialogBuilder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val dialogView = inflater.inflate(R.layout.submit_dialog, null)
            dialogBuilder.setView(dialogView)

            val spinner = dialogView.findViewById<View>(R.id.spinner) as Spinner
            val spinnerAdapter = ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                titles!!
            ) //selected item will look like a spinner set from XML
            spinnerAdapter.setDropDownViewResource(
                android.R.layout
                    .simple_spinner_dropdown_item
            )
            spinner.adapter = spinnerAdapter

            val buttonUpdate = dialogView.findViewById<View>(R.id.buttonSubmit) as Button

            dialogBuilder.setTitle("Select Work to Edit/Delete")
            // Log.e("help",mDatabaseUsers!!.child(authorId).child("Name").toString())
            val b = dialogBuilder.create()
            b.show()


            buttonUpdate.setOnClickListener {

                val selected: String
                selected = spinner!!.selectedItem.toString()
                Toast.makeText(applicationContext, "Submitting "+selected, Toast.LENGTH_LONG).show()

                val current_challenge = intent.getStringExtra("CHALLENGE_NAME")

                val uid = intent.getStringExtra(UserID)
                mDatabaseChallenges!!.child(current_challenge).child(selected).setValue(works!![titles!!.indexOf(selected)])

                b.dismiss()

            }

    }


companion object {
    val UserID = "com.example.tesla.myhomelibrary.UID"
}
}


