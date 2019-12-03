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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SubmitChallengeActivity : AppCompatActivity() {

    private var worksView: ListView? = null


    private var titles: MutableList<String>? = null

    internal var spinnerCountry: Spinner ?= null

    private var progressBar: ProgressBar? = null
    private var submitBtn: Button? = null
    private var mDatabaseChallenges: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.submit_challenge)


        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseChallenges = mDatabase!!.reference!!.child("Challenges")

        titles?.add("owo")
        titles?.add("uwu")

        initializeUI()
        submitBtn!!.setOnClickListener { submitChallengeWork() }


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


        submitBtn = findViewById(R.id.submitButton)

        progressBar = findViewById(R.id.progressBar)

    }

    private fun submitChallengeWork(){
        progressBar!!.visibility = View.VISIBLE

        val selected_work: String
        selected_work = spinnerCountry!!.selectedItem.toString()

        if (TextUtils.isEmpty(selected_work)) {
            Toast.makeText(applicationContext, "Please select a work...", Toast.LENGTH_LONG).show()
            return
        }
        else {
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
            mDatabaseChallenges!!.child(current_challenge).child(uid).setValue(selected_work)


            //displaying a success toast

            progressBar!!.visibility = View.INVISIBLE
            Toast.makeText(this, "Challenge added", Toast.LENGTH_LONG).show()
        }





        }



companion object {
    val CHALLENGE_NAME = "com.example.tesla.myhomelibrary.authorname"
    val AUTHOR_ID = "com.example.tesla.myhomelibrary.authorid"
    val UserID = "com.example.tesla.myhomelibrary.UID"
}
}


