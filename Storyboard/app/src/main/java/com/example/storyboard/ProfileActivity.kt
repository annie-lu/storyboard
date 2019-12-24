package com.example.storyboard

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.content.Intent
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.worksview_item.view.*


class ProfileActivity : AppCompatActivity() {

    private var nameTV: EditText? = null
    private var bioTV : EditText? = null
    private var editButton : ImageButton? = null
    private var worksView: ListView? = null

    private var titles: MutableList<String>? = null
    private var mDatabaseUsers: DatabaseReference? = null
    private var mDatabaseTitles: DatabaseReference? = null

    private var worksPassed: String = ""

    private var editing = false
    private var profUID: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        initializeUI()

        intent.getStringExtra("VIEWUSER")?.let {
            profUID = it
        }

        if (!profUID.equals(intent.getStringExtra("CURRUSER"))) {
            editButton?.visibility = View.INVISIBLE
            editButton?.isEnabled = false
        }
        else {
            editButton?.visibility = View.VISIBLE
            editButton?.isEnabled = true
        }

        intent.getStringExtra("WORKS")?.let {
            worksPassed = it
        }

        editButton?.setOnClickListener {
            editing = !editing

            if (editing) {
                (it as ImageButton).setColorFilter(Color.parseColor("#00BCD4"))

            }
            else {
                (it as ImageButton).setColorFilter(android.R.color.transparent)
            }

            Toast.makeText(this, "Click button detected", Toast.LENGTH_SHORT).show()
            nameTV?.isEnabled = !(nameTV!!.isEnabled)
            bioTV?.isEnabled = !(bioTV!!.isEnabled)
        }


    }

    override fun onStart() {
        super.onStart()

    }

    //Saving user bio/name changes to firebase
    override fun onPause() {
        super.onPause()

        mDatabaseUsers?.child(profUID)?.child("Name")?.setValue(nameTV?.text.toString())
        mDatabaseUsers?.child(profUID)?.child("Bio")?.setValue(bioTV?.text.toString())

    }


    override fun onResume() {
        super.onResume()

        mDatabaseUsers?.child(profUID)?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                titles?.clear()
                nameTV?.setText(dataSnapshot.child("Name").value.toString())
                bioTV?.setText(dataSnapshot.child("Bio").value.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.i("HELP", "error: " + databaseError.message)
            }
        })

        if (!worksPassed.equals("")) {

            var rawWorksList = worksPassed.split(", ")
            mDatabaseTitles = FirebaseDatabase.getInstance().getReference("Titles")
            mDatabaseTitles!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (w in rawWorksList) {
                        titles?.add(dataSnapshot.child(w).child("title").value.toString())
                    }

                    val adapter = ArrayAdapter(
                        this@ProfileActivity,
                        R.layout.worksview_item,
                        R.id.workTitle, titles!!
                    )

                    worksView?.adapter = adapter


                    worksView?.onItemClickListener =
                        AdapterView.OnItemClickListener { adapterView, view, i, l ->
                            val WorksActivityIntent = Intent(applicationContext, WorksActivity::class.java)
                            val selected = view.workTitle.text.toString()
                            Toast.makeText(applicationContext, "Editing "+selected, Toast.LENGTH_SHORT).show()

                            WorksActivityIntent.putExtra("TITLE",selected)
                            WorksActivityIntent.putExtra("CURRUSER",intent.getStringExtra("CURRUSER"))
                            WorksActivityIntent.putExtra("WORKS",worksPassed)
                            WorksActivityIntent.putExtra("WORKID", rawWorksList!![titles!!.indexOf(selected)])
                            startActivity(WorksActivityIntent)
                        }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.i("HELP", "error = " + databaseError.message)
                }
            })
        }
    }
    private fun initializeUI() {
        nameTV = findViewById<EditText>(R.id.name)
        bioTV = findViewById<EditText>(R.id.bio)
        editButton = findViewById<ImageButton>(R.id.editButton)
        worksView = findViewById(R.id.worksView)


            titles = ArrayList()



        nameTV?.isEnabled = false
        bioTV?.isEnabled = false



        mDatabaseUsers = FirebaseDatabase.getInstance().getReference("Users")

    }



}