package com.example.storyboard

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import android.widget.ArrayAdapter
import android.widget.Spinner


class SubmitChallengeActivity : AppCompatActivity() {

    private var worksView: ListView? = null


    private var titles: MutableList<String>? = null
    private var challenges: MutableList<String>? = null

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

        challenges = ArrayList()
        challenges?.add("5k Words")
        challenges?.add("Creative Prompt")


        initializeUI()
        submitBtn!!.setOnClickListener { submitChallengeWork() }


    }

    private fun initializeUI() {


        val adapter = ArrayAdapter(this,
            R.layout.worksview_item,
            R.id.workTitle, challenges!!)

        worksView = findViewById(R.id.worksView)
        worksView?.adapter = adapter

        val spinner = findViewById<View>(R.id.spinner) as Spinner
        val spinnerAdapter = ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item,
            titles!!
        ) //selected item will look like a spinner set from XML
        spinnerAdapter.setDropDownViewResource(
            android.R.layout
                .simple_spinner_dropdown_item
        )
        spinner.adapter = spinnerAdapter


        submitBtn = findViewById(R.id.submitButton)

        progressBar = findViewById(R.id.progressBar)

    }

    private fun submitChallengeWork(){
        progressBar!!.visibility = View.VISIBLE

        val selected: String
        selected = spinner!!.selectedItem.toString()


            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Author


            //Saving the Author
            Log.e("ohno", "attempts to create a challenge")
            val current_challenge = intent.getStringExtra(CHALLENGE_NAME)
            if( current_challenge == null) {
                Log.i("help","this is null")
            }else{
                Log.i("help",current_challenge)
            }
            val uid = intent.getStringExtra(UserID)
            mDatabaseChallenges!!.child(current_challenge).child(uid).setValue(selected)


            //displaying a success toast

            progressBar!!.visibility = View.INVISIBLE
            Toast.makeText(this, "Challenge added", Toast.LENGTH_LONG).show()





        }



companion object {
    val CHALLENGE_NAME = "com.example.tesla.myhomelibrary.authorname"
    val AUTHOR_ID = "com.example.tesla.myhomelibrary.authorid"
    val UserID = "com.example.tesla.myhomelibrary.UID"
}
}


