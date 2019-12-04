package com.example.storyboard

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.print.PrintAttributes
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.marginTop
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*
import kotlin.math.log
import kotlin.math.truncate


class CommunityActivity : AppCompatActivity() {

    private var mDatabaseUsers: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    internal lateinit var works: ArrayList<String>
    private lateinit var user: String
    private lateinit var workidString: String

    private var users: MutableList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("community", "entered on create")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseUsers = FirebaseDatabase.getInstance().reference

        works = ArrayList()

        workidString = ""

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
                    params.width = resources.getDimensionPixelSize(R.dimen.profileButton)
                    params.height = resources.getDimensionPixelSize(R.dimen.profileButton)
                    btnTag.layoutParams = params
                    btnTag.ellipsize = TextUtils.TruncateAt.END
                    btnTag.maxLines = 1


                    btnTag.setText(author)
                    btnTag.id = x
                    btnTag.setBackgroundResource(R.drawable.dashboardbutton)
                    btnTag.setTextColor(Color.parseColor("#ffffff"))

                    btnTag.setOnClickListener {
                        val profileIntent = Intent(applicationContext, ProfileActivity::class.java)

                        val currentUser = FirebaseAuth.getInstance().currentUser!!.uid

                        workidString = postSnapshot.child("Works").value.toString()

                        Log.i("search this", viewUser)
                        profileIntent.putExtra("CURRUSER", currentUser)
                        profileIntent.putExtra("VIEWUSER", viewUser)
                        profileIntent.putExtra("WORKS", workidString)

                        startActivity(profileIntent)
                    }

                    //add button to the layout
                    layout.addView(btnTag)
                }

            } override fun onCancelled(databaseError: DatabaseError) {
            }
        })

        //fill suggested
        mDatabaseUsers!!.child("Titles").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.i("community", "made it here")
                val numWorks = dataSnapshot.childrenCount
                val suggestedLayout = findViewById(R.id.linearLayoutSuggestions) as LinearLayout
                suggestedLayout.removeAllViews()
                works.clear()
                var i = 0
                var r = Random()
                var ranNums = (1..5).map { r.nextInt((numWorks.toInt() + 1)) }
                Log.i("random", ranNums.toString())

                for (postSnapshot in dataSnapshot.children) {
                    i++
                    val work = postSnapshot.child("title").value.toString()
                    works.add(work)
                    if (ranNums.contains(i)) {

                        val textBox = TextView(applicationContext)

                        textBox.setLayoutParams(ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ActionBar.LayoutParams.WRAP_CONTENT
                        ))

                        textBox.setText(work)
                        textBox.textSize = 20f
                        textBox.gravity = Gravity.CENTER_HORIZONTAL

                        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                        params.setMargins(10,10,10,10)

                        suggestedLayout.addView(textBox)
                    }

                }
            }

            override fun onCancelled(p0: DatabaseError) {}
        })
    }
}
