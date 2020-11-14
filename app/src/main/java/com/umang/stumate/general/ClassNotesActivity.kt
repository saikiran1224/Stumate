package com.umang.stumate.general

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.umang.stumate.R
import com.umang.stumate.adapters.ClassNotesAdapter
import com.umang.stumate.modals.FileUploadData
import com.umang.stumate.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_class_notes.*


class ClassNotesActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var classNotesList: ArrayList<FileUploadData>
    private lateinit var classNotesAdapter: ClassNotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_notes)

        AppPreferences.init(this)

        closeButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        classNotesList = ArrayList<FileUploadData>()

        // ClassNotesAdapter Layout Manager
        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        linearLayoutManager.reverseLayout = false


        sortbyDate.setOnClickListener {
            linearLayoutManager.reverseLayout = true
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
            }
        })


/*
        filterSubjects.setOnClickListener {
            startActivity(Intent(this, FilterSubjectsActivity::class.java))
        }
*/
        searchFiles.setOnClickListener {
            sortbyDate.visibility = GONE
            searchFiles.visibility = GONE
            divider.visibility = GONE

            // Showing Search Edit Text
            searchEditText.visibility = VISIBLE
        }

        // Retrieving Data from Firebase Realtime Database
        retriveClassNotesData()
    }

    private fun retriveClassNotesData() {

        val myRef = FirebaseDatabase.getInstance().reference.child(AppPreferences.studentID).child("files_data")
        myRef.keepSynced(true)

        val classNotesListener = object :ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                classNotesList.clear()
                for (ds in dataSnapshot.children) {
                    val classNotesData = ds.getValue(FileUploadData::class.java)

                    if (classNotesData != null) {
                        classNotesList.add(classNotesData)

                        classNotesAdapter = ClassNotesAdapter(
                            this@ClassNotesActivity,
                            classNotesList
                        )

                        classNotesRecycler.setHasFixedSize(true)
                        linearLayoutManager.reverseLayout = false
                        linearLayoutManager.stackFromEnd = true
                        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

                        classNotesRecycler.layoutManager = linearLayoutManager
                        classNotesRecycler.adapter = classNotesAdapter
                        classNotesAdapter.notifyDataSetChanged()

                    }
                }


            } override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ClassNotesActivity, error.message, Toast.LENGTH_LONG).show()
            }
        }
        myRef.addValueEventListener(classNotesListener)


    }

    private fun filter(text: String) {
        val filteredlist: ArrayList<FileUploadData> = ArrayList()
        for (item in classNotesList) {
            if (item.fileName!!.toLowerCase().contains(text.toLowerCase()) || item.subjectName?.toLowerCase()!!.contains(text.toLowerCase()) || item.unitNumber?.toLowerCase()!!.contains(text.toLowerCase()) || item.studentName?.toLowerCase()!!.contains(text.toLowerCase()) || item.fileType?.toLowerCase()!!.contains(text.toLowerCase())) {
                filteredlist.add(item)
            }
        }
        classNotesAdapter.filterList(filteredlist)
    }



}