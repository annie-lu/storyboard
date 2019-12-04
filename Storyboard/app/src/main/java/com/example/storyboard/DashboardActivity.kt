package com.example.storyboard

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

private var mDatabaseUsers: DatabaseReference? = null
private var mDatabase: FirebaseDatabase? = null
private var works: String = ""

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)



        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseUsers = mDatabase!!.getReference("Users")

        val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
        mDatabaseUsers!!.child(currentUser).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                works = dataSnapshot.child("Works").value.toString()
            } override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    fun loadProfileActivity(v: View) {
        //Toast.makeText(this, "Loading Profiles", Toast.LENGTH_SHORT).show()

        val profileIntent = Intent(applicationContext, ProfileActivity::class.java)

        //TODO send the current users name so it can be authorized - make sure strings are right
        val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
        profileIntent.putExtra("CURRUSER", currentUser)
        profileIntent.putExtra("VIEWUSER", currentUser)
        profileIntent.putExtra("WORKS", works)

        startActivity(profileIntent)
    }

    fun loadCommunityActivity(v: View) {
        //Toast.makeText(this, "Loading Community", Toast.LENGTH_SHORT).show()

        val communityIntent = Intent(applicationContext, CommunityActivity::class.java)
        startActivity(communityIntent)
    }

    fun loadChallengesActivity(v: View) {
        Toast.makeText(this, "Loading Challenges", Toast.LENGTH_SHORT).show()


        val challengeIntent = Intent(applicationContext, ChallengeActivity::class.java)
        challengeIntent.putExtra(UserID,intent.getStringExtra(UserID))
        challengeIntent.putExtra("WORKS",works)
        startActivity(challengeIntent)
    }

    fun loadWorksActivity(v: View) {
        Toast.makeText(this, "Loading Works", Toast.LENGTH_SHORT).show()
        val currentUser = FirebaseAuth.getInstance().currentUser!!.uid

        val worksIntent= Intent(applicationContext, WorksDashActivity::class.java)
        worksIntent.putExtra("CURRUSER", currentUser)
        worksIntent.putExtra("VIEWUSER", currentUser)
        worksIntent.putExtra("WORKS", works)
        startActivity(worksIntent)
    }


    companion object {
        val UserMail = "com.example.tesla.myhomelibrary.UMail"
        val UserID = "com.example.tesla.myhomelibrary.UID"
    }
}
