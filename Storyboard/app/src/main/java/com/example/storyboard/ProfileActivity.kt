package com.example.storyboard

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*


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

        //nameTV?.inputType = InputType.TYPE_NULL
        //nameTV?.isEnabled = false
        //nameTV?.isFocusable = false
        //nameTV?.isClickable = true

        // TODO - MAKE IT SO ONLY YOU CAN EDIT OWN PROFILE, GET USERID FROM EXTRA
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




//        //NOT WORKING
//        nameTV?.setOnClickListener {
//            Toast.makeText(this, "Long click detected", Toast.LENGTH_SHORT).show()
//            nameTV?.isEnabled = true
//            Log.i("HELP", "name clicked")
//            true
//        }
//
//        bioTV?.setOnLongClickListener {
//            Toast.makeText(this, "Long click detected", Toast.LENGTH_SHORT).show()
//            bioTV?.isEnabled = true
//            Log.i("HELP", "bio clicked")
//
//            true
//        }
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
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.i("HELP", "error = " + databaseError.message)
                }
            })
        }
    }

//    override fun onResume() {
//        super.onResume()
//
//        Log.i("HELP", "entered onresume")
//
//
//
//        mDatabaseUsers?.child(profUID)?.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//
//                Log.i("HELP", "name from firebase: " + dataSnapshot.child("Name").value.toString())
//
//                nameTV?.setText(dataSnapshot.child("Name").value.toString())
//                bioTV?.setText(dataSnapshot.child("Bio").value.toString())
//
//                // ...
//                //});
//                //nameTV?.text = dataSnapshot.child(profUID).child("Name").getValue(String.class)
//
////                if (dataSnapshot.child(profUID).hasChild("Name")) {
////
////                }
//
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                Log.i("HELP", "error: " + databaseError.message)
//
//            }
//        })
//    }


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