package com.umang.stumate.general

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.umang.stumate.R
import com.umang.stumate.adapters.ClassMatesAdapter
import com.umang.stumate.adapters.ClassNotesAdapter
import com.umang.stumate.modals.FileUploadData
import com.umang.stumate.modals.StudentData
import com.umang.stumate.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_class_mates.*
import kotlinx.android.synthetic.main.activity_class_notes.*
import kotlinx.android.synthetic.main.activity_class_notes.closeButton

class ClassMatesActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var classMatesList: ArrayList<StudentData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_mates)

        AppPreferences.init(this)

        closeButton.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
        }

        classMatesList = ArrayList<StudentData>()

        // ClassNotesAdapter Layout Manager
        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        linearLayoutManager.reverseLayout = false

        // Retrieving Data from Firebase Realtime Database
        retreiveClassNotesData()
    }

    private fun retreiveClassNotesData() {

        val myRef = FirebaseDatabase.getInstance().reference.child("students_data").orderByChild("studentID").equalTo(AppPreferences.studentID)

        val classMatesListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val classMatesData = ds.getValue(StudentData::class.java)
                    if (classMatesData != null) {
                        classMatesList.add(classMatesData)
                    }
                }
                val classMatesAdapter = ClassMatesAdapter(baseContext,classMatesList)
                classMatesRecycler.layoutManager = linearLayoutManager
                classMatesRecycler.adapter = classMatesAdapter
            } override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext,error.message, Toast.LENGTH_LONG).show()
            }
        }
        myRef.addValueEventListener(classMatesListener)

    }

}