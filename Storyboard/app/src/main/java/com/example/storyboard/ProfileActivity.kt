package com.example.storyboard

import android.graphics.Color
import android.graphics.PorterDuff
import android.media.Image
import android.os.Bundle
import android.os.PersistableBundle
import android.text.InputType
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*


class ProfileActivity : AppCompatActivity() {

    private var nameTV: EditText? = null
    private var bioTV : EditText? = null
    private var editButton : ImageButton? = null
    private var worksView: ListView? = null

    private var titles: MutableList<String>? = null
    private var mDatabase: DatabaseReference? = null

    private var editing = false
    private var uid: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        initializeUI()

        intent.getStringExtra("CURRUSER")?.let {
            uid = it
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


        //NOT WORKING
        nameTV?.setOnClickListener {
            Toast.makeText(this, "Long click detected", Toast.LENGTH_SHORT).show()
            nameTV?.isEnabled = true
            Log.i("HELP", "name clicked")
            true
        }

        bioTV?.setOnLongClickListener {
            Toast.makeText(this, "Long click detected", Toast.LENGTH_SHORT).show()
            bioTV?.isEnabled = true
            Log.i("HELP", "bio clicked")

            true
        }
    }

    override fun onStart() {
        super.onStart()
        mDatabase?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //clearing the previous artist list
                titles?.clear()
                // getting authors only for the Current User

//                Toast.makeText(applicationContext,
//                    "uid: " + uid, Toast.LENGTH_LONG).show()

                nameTV?.setText(dataSnapshot.child(uid).child("Name").value.toString())
                bioTV?.setText(dataSnapshot.child(uid).child("Bio").value.toString())


                for (postSnapshot in dataSnapshot.child(uid).child("Works").children) {

//                    Toast.makeText(applicationContext,
//                        "key: " + postSnapshot.key, Toast.LENGTH_LONG).show()


                    //if (postSnapshot.key == user) {
                        val tit = postSnapshot.value.toString()
                        titles?.add(tit)
                    //}

                }

                //iterating through all the nodes
                //getting artist
                //adding author to the list

                //creating adapter using AuthorList
                //attaching adapter to the listview

//                val authorAdapter = AuthorList(this@DashboardActivity, authors)
//                listViewAuthors.adapter = authorAdapter

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    //Saving user bio/name changes to firebase
    override fun onPause() {
        super.onPause()

        mDatabase?.child(uid)?.child("Name")?.setValue(nameTV?.text.toString())
        mDatabase?.child(uid)?.child("Bio")?.setValue(bioTV?.text.toString())

    }


    override fun onResume() {
        super.onResume()

        Log.i("HELP", "entered onresume")

        mDatabase?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                nameTV?.setText(dataSnapshot.child(uid).child("Name").value.toString())
                bioTV?.setText(dataSnapshot.child(uid).child("Bio").value.toString())

                // ...
                //});
                //nameTV?.text = dataSnapshot.child(uid).child("Name").getValue(String.class)

//                if (dataSnapshot.child(uid).hasChild("Name")) {
//
//                }

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }


    private fun initializeUI() {
        nameTV = findViewById<EditText>(R.id.name)
        bioTV = findViewById<EditText>(R.id.bio)
        editButton = findViewById<ImageButton>(R.id.editButton)
        worksView = findViewById(R.id.worksView)

        titles = ArrayList()

        val adapter = ArrayAdapter(this,
            R.layout.worksview_item,
            R.id.workTitle, titles!!)

        worksView?.adapter = adapter

        nameTV?.isEnabled = false
        bioTV?.isEnabled = false

        mDatabase = FirebaseDatabase.getInstance().getReference("Users")

    }



}