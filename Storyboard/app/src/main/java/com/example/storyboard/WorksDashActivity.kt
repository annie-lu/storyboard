package com.example.storyboard

import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class WorksDashActivity: AppCompatActivity() {

    private var createBtn: Button? = null
    private var editBtn: Button? = null

    private var mDatabaseUsers: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var name: String? = null
    private var works: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_worksdash)

        val user = intent.getStringExtra("CURRUSER")

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseUsers = mDatabase!!.getReference("Users")

        mDatabaseUsers!!.child(user).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                name = dataSnapshot.child("Name").value.toString()
                works = dataSnapshot.child("Works").value.toString()
            } override fun onCancelled(databaseError: DatabaseError) {
            }
        })

        initializeUI()

        createBtn?.setOnClickListener {
            showCreateDialog(name!!, works!!)
            true
        }

        editBtn?.setOnClickListener {
            showEditDialog(name!!, works!!)
            true
        }
    }

    private fun showEditDialog(authorName: String, authorWorks: String) {

        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.edit_dialog, null)
        dialogBuilder.setView(dialogView)

        val spinnerCountry = dialogView.findViewById<View>(R.id.spinnerCountry) as Spinner
        val buttonUpdate = dialogView.findViewById<View>(R.id.buttonUpdate) as Button
        val buttonDelete = dialogView.findViewById<View>(R.id.buttonDelete) as Button

        dialogBuilder.setTitle("Select Work to Edit/Delete")
        // Log.e("help",mDatabaseUsers!!.child(authorId).child("Name").toString())
        val b = dialogBuilder.create()
        b.show()


        buttonUpdate.setOnClickListener {
            val country = spinnerCountry.selectedItem.toString()
            if (!TextUtils.isEmpty(name)) {
                //TODO: HERE WE SHOULD PUT THE WORKS ACTIVITY INTENT????
                b.dismiss()
            }
        }


        buttonDelete.setOnClickListener {

            //TODO: HERE WE SHOULD DELETE THE SELECTED WORK
            b.dismiss()
        }
    }

    private fun showCreateDialog(authorName: String, authorWorks: String) {

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