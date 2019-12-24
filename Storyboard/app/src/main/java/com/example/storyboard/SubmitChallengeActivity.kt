package com.example.storyboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import android.util.Log
import kotlinx.android.synthetic.main.worksview_item.view.*

class SubmitChallengeActivity : AppCompatActivity() {

    private var worksView: ListView? = null
    private var promptView: TextView? = null

    private var titles: MutableList<String>? = null
    private var submissions: MutableList<String>? = null


    private var progressBar: ProgressBar? = null
    private var submitBtn: Button? = null
    private var mDatabaseChallenges: DatabaseReference? = null

    private var mDatabaseTitles: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null

    private var works: List<String>? = null

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
                    if( w!="") {
                        titles?.add(dataSnapshot.child(w).child("title").value.toString())
                    }
                }
            } override fun onCancelled(databaseError: DatabaseError) {
            }
        })


        submissions = ArrayList()
        mDatabaseChallenges!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for( w in dataSnapshot.child((intent.getStringExtra("CHALLENGE_NAME"))).children){
                    if(w.key.toString()!="Prompt"){
                        submissions?.add(w.key.toString())
                        Log.e("excuse me what",w.key.toString())
                    }
                }
                promptView!!.setText("Submit your work to complete this challenge! We add the challenge description here! " +dataSnapshot.child((intent.getStringExtra("CHALLENGE_NAME"))).child("Prompt").value)
            } override fun onCancelled(databaseError: DatabaseError) {
            }
        })

        initializeUI()

        submitBtn!!.setOnClickListener {
            if(intent.getStringExtra("WORKS")==""){
            Toast.makeText(applicationContext, "You have no works to submit", Toast.LENGTH_LONG).show()
        }else{submitDialog()} }

    }

    private fun initializeUI() {

        promptView = findViewById(R.id.bio)

        worksView = findViewById(R.id.worksView)
        val adapter = ArrayAdapter(this,
            R.layout.worksview_item,
            R.id.workTitle, submissions!!)
        worksView?.adapter = adapter
        worksView?.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val WorksActivityIntent = Intent(applicationContext, WorksActivity::class.java)
                val selected = view.workTitle.text.toString()
                Toast.makeText(applicationContext, "Editing "+selected, Toast.LENGTH_SHORT).show()

                WorksActivityIntent.putExtra("TITLE",selected)
                WorksActivityIntent.putExtra("CURRUSER",intent.getStringExtra("CURRUSER"))
                WorksActivityIntent.putExtra("WORKS",intent.getStringExtra("WORKS"))
                WorksActivityIntent.putExtra("WORKID", works!![titles!!.indexOf(selected)])
                startActivity(WorksActivityIntent)
            }

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
            val b = dialogBuilder.create()
            b.show()


            buttonUpdate.setOnClickListener {

                val selected: String
                selected = spinner!!.selectedItem.toString()
                Toast.makeText(applicationContext, "Submitting "+selected, Toast.LENGTH_SHORT).show()

                val current_challenge = intent.getStringExtra("CHALLENGE_NAME")

                mDatabaseChallenges!!.child(current_challenge).child(selected).setValue(works!![titles!!.indexOf(selected)])

                b.dismiss()

            }

    }

}


