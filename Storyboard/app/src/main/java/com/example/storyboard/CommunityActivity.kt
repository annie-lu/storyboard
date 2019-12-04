package com.example.storyboard

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.print.PrintAttributes
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class CommunityActivity : AppCompatActivity() {

    private var mDatabaseUsers: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var name: String? = null
    private var works: String? = null
    private lateinit var user: String

    private var users: MutableList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("community", "entered on create")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseUsers = FirebaseDatabase.getInstance().reference

    }

    override fun onStart() {
        super.onStart()
        val layout = findViewById(R.id.linearLayout) as LinearLayout
        var x = 0
        Log.i("community", "entered onStart")
        layout.removeAllViews()

        mDatabaseUsers!!.child("Users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.i("community", "made it here")
                for (postSnapshot in dataSnapshot.children) {
                    x++
                    val author = postSnapshot.child("Name").value.toString()
                    val viewUser = postSnapshot.key

                    //Create a button
                    val btnTag = Button(applicationContext)

                    btnTag.setLayoutParams(ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.WRAP_CONTENT
                    ))

                    val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                    params.setMargins(10,10,10,10)
                    btnTag.layoutParams = params


                    btnTag.setText(author)
                    btnTag.id = x
                    btnTag.setBackgroundResource(R.drawable.dashboardbutton)
                    btnTag.setTextColor(Color.parseColor("#ffffff"))

                    btnTag.setOnClickListener {
                        val profileIntent = Intent(applicationContext, ProfileActivity::class.java)

                        val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
                        Log.i("search this", viewUser)
                        profileIntent.putExtra("CURRUSER", currentUser)
                        profileIntent.putExtra("VIEWUSER", viewUser)

                        startActivity(profileIntent)
                    }

                    //add button to the layout
                    layout.addView(btnTag)
                }

            } override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }
}
