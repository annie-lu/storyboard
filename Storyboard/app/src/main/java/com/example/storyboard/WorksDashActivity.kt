package com.example.storyboard

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class WorksDashActivity: AppCompatActivity() {

    private var createBtn: Button? = null
    private var editBtn: Button? = null

    private var mDatabaseTitles: DatabaseReference? = null
    private var mDatabaseUsers: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var name: String? = null
    private var works: String? = null
    private var userWorks: List<String>? = null
    private var titles: MutableList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_worksdash)

        val user = intent.getStringExtra("CURRUSER")


        if(works==null){
            Log.e("ahhh","works not currently working")
            works = intent.getStringExtra("WORKS")
        }
        userWorks = works!!.split(", ")

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseTitles = mDatabase!!.reference!!.child("Titles")
        titles = ArrayList()
        mDatabaseTitles!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(w in userWorks!!){
                    titles?.add(dataSnapshot.child(w).child("title").value.toString())
                }
            } override fun onCancelled(databaseError: DatabaseError) {
            }
        })

        initializeUI()

        createBtn?.setOnClickListener {
            showCreateDialog()
            true
        }

        editBtn?.setOnClickListener {
            showEditDialog()
            true
        }
    }

    private fun showEditDialog() {

        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.edit_dialog, null)
        dialogBuilder.setView(dialogView)

        val spinnerCountry = dialogView.findViewById<View>(R.id.spinnerCountry) as Spinner
        val spinnerAdapter = ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item,
            titles!!
        ) //selected item will look like a spinner set from XML
        spinnerAdapter.setDropDownViewResource(
            android.R.layout
                .simple_spinner_dropdown_item
        )
        spinnerCountry.adapter = spinnerAdapter

        val buttonUpdate = dialogView.findViewById<View>(R.id.buttonUpdate) as Button
        val buttonDelete = dialogView.findViewById<View>(R.id.buttonDelete) as Button

        dialogBuilder.setTitle("Select Work to Edit/Delete")
        // Log.e("help",mDatabaseUsers!!.child(authorId).child("Name").toString())
        val b = dialogBuilder.create()
        b.show()


        buttonUpdate.setOnClickListener {
            val country = spinnerCountry.selectedItem.toString()
                //TODO: HERE WE SHOULD PUT THE WORKS ACTIVITY INTENT????

            val selected: String
            selected = spinnerCountry!!.selectedItem.toString()
            Toast.makeText(applicationContext, selected, Toast.LENGTH_LONG).show()
                val WorksActivityIntent = Intent(applicationContext, WorksActivity::class.java)
                WorksActivityIntent.putExtra("TITLE",selected)
                WorksActivityIntent.putExtra("CURRUSER",intent.getStringExtra("CURRUSER"))
                WorksActivityIntent.putExtra("WORKS",works)
                WorksActivityIntent.putExtra("WORKID", userWorks!![titles!!.indexOf(selected)])
                startActivity(WorksActivityIntent)
                b.dismiss()

        }


        buttonDelete.setOnClickListener {

            //TODO: HERE WE SHOULD DELETE THE SELECTED WORK
            b.dismiss()
        }
    }

    private fun showCreateDialog() {

        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.create_dialog, null)
        dialogBuilder.setView(dialogView)

        val editTextName = dialogView.findViewById<View>(R.id.editTextName) as EditText
        val buttonCreate = dialogView.findViewById<View>(R.id.buttonCreate) as Button

        dialogBuilder.setTitle("Create Work Title")
       // Log.e("help",mDatabaseUsers!!.child(authorId).child("Name").toString())
        val b = dialogBuilder.create()
        b.show()

        buttonCreate.setOnClickListener {
            val titleName = editTextName.text.toString().trim { it <= ' ' }
            if (!TextUtils.isEmpty(titleName)) {
                val WorksActivityIntent = Intent(applicationContext, WorksActivity::class.java)
                WorksActivityIntent.putExtra("TITLE",titleName)
                WorksActivityIntent.putExtra("CURRUSER",intent.getStringExtra("CURRUSER"))
                WorksActivityIntent.putExtra("WORKS",works)
                WorksActivityIntent.putExtra("WORKID", mDatabase!!.getReference("Works").push().key)
                startActivity(WorksActivityIntent)
                //TODO: HERE WE SHOULD PUT THE WORKS ACTIVITY INTENT????
                b.dismiss()
            }
        }

    }

    private fun initializeUI() {
        createBtn = findViewById(R.id.create) as Button
        editBtn = findViewById(R.id.edit) as Button
    }

}