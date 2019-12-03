package com.example.storyboard

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.print.PrintAttributes
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class CommunityActivity : AppCompatActivity() {

    internal lateinit var databaseAuthors: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        databaseAuthors = FirebaseDatabase.getInstance().getReference("Titles")

        createLayout()
    }

    @SuppressLint("ResourceType")
    fun createLayout() {
        val layout = findViewById(R.id.linearLayout) as LinearLayout

        //set the properties for button
        for (x in 0..10) {
            val btnTag = Button(this)

            btnTag.setLayoutParams(ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT
            ))

            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(10,10,10,10)
            btnTag.layoutParams = params


            btnTag.setText("Button")
            btnTag.id = x
            btnTag.setBackgroundResource(R.drawable.dashboardbutton)
            btnTag.setTextColor(Color.parseColor("#ffffff"))

            btnTag.setOnClickListener {
                val profileIntent = Intent(applicationContext, ProfileActivity::class.java)

                val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
                profileIntent.putExtra("CURRUSER", currentUser)
                profileIntent.putExtra("VIEWUSER", currentUser)

                startActivity(profileIntent)
            }


            //add button to the layout
            layout.addView(btnTag)
        }
    }

    // Todo onStart
    /*override fun onStart() {
        super.onStart()
        databaseAuthors.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //clearing the previous artist list
                authors.clear()

                // getting authors only for the Current User
                //iterating through all the nodes
                for (postSnapshot in dataSnapshot.children) {
                    //getting artist
                    //adding author to the list
                    var currentUser = FirebaseAuth.getInstance().currentUser!!.uid
                    if(postSnapshot.key == currentUser) {
                        for (postSnapshot2 in postSnapshot.children) {
                            val author = postSnapshot2.getValue<Author>(Author::class.java)
                            authors.add(author!!)
                        }
                    }
                }
                val authorListAdapter = AuthorList(this@DashboardActivity, authors)
                listViewAuthors.adapter = authorListAdapter
                //creating adapter using AuthorList
                //attaching adapter to the listview
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }*/
}
