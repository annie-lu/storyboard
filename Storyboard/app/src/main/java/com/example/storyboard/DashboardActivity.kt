package com.example.storyboard

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
    }

    fun loadProfileActivity(v: View) {
        //Toast.makeText(this, "Loading Profiles", Toast.LENGTH_SHORT).show()

        val profileIntent = Intent(applicationContext, ProfileActivity::class.java)

        //TODO send the current users name so it can be authorized - make sure strings are right
        val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
        profileIntent.putExtra("CURRUSER", currentUser)
        profileIntent.putExtra("VIEWUSER", currentUser)

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
        startActivity(challengeIntent)
    }

    fun loadWorksActivity(v: View) {
        Toast.makeText(this, "Loading Works", Toast.LENGTH_SHORT).show()

        val worksIntent= Intent(applicationContext, WorksActivity::class.java)
        startActivity(worksIntent)
    }
}
