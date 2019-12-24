package com.example.storyboard

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

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
            finish()
        }

        saveBtn?.setOnClickListener {
            val id = mDatabaseWorks!!.push().key

            mDatabaseWorks!!.child(workid!!).child("content").setValue(worksContent!!.text.toString().trim() )

            if (!intent.getStringExtra("WORKS").contains(workid.toString().trim()))
                if(intent.getStringExtra("WORKS")!=""){
                    mDatabaseUsers!!.child("Works").setValue(intent.getStringExtra("WORKS")+", "+workid.toString().trim() )
                }else{
                    mDatabaseUsers!!.child("Works").setValue(workid.toString().trim())
                }

            mDatabaseTitles!!.child(workid!!).child("title").setValue(worksTitle!!.text.toString().trim())
            mDatabaseWorks!!.child(workid!!).child("title").setValue(worksTitle!!.text.toString().trim())

            Toast.makeText(applicationContext, "Saving "+worksTitle!!.text.toString().trim(), Toast.LENGTH_SHORT).show()


            true
        }

    }

        private fun initializeUI() {

            worksTitle = findViewById<EditText>(R.id.title)
            worksContent = findViewById<EditText>(R.id.content)
            cancelBtn = findViewById(R.id.cancel) as Button
            saveBtn = findViewById(R.id.save) as Button

            worksTitle?.setText(intent.getStringExtra("TITLE").toString())

            mDatabaseWorks?.child(workid)?.addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.hasChild("content"))
                            worksContent?.setText(dataSnapshot.child("content").value.toString())
                    }

                    override fun onCancelled(p0: DatabaseError) {

                    }
                }
            )
        }
}