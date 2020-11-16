package com.umang.stumate.general

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.umang.stumate.R
import com.umang.stumate.adapters.ClassNotesAdapter
import com.umang.stumate.auth.AuthenticationActivity
import com.umang.stumate.modals.FileUploadData
import com.umang.stumate.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_class_notes.*
import kotlinx.android.synthetic.main.activity_class_notes.searchEditText


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

        val  intent = intent
        if(intent.getStringExtra("navigatedFrom")!=null) {

            sortbyDate.visibility = GONE
            searchFiles.visibility = GONE
            divider.visibility = GONE

            // Showing Search Edit Text
            searchEditText.visibility = VISIBLE
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
            }
        })

        animationView.visibility = VISIBLE
        prefsLayout.visibility = GONE
        recyclerLayout.visibility = GONE


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

        classNotesFAB.setOnClickListener {
            startActivity(Intent(this, UploadFilesActivity::class.java))
        }


        classNotesBottomNav.setOnClickListener {
            val dialog= BottomSheetDialog(this,R.style.BottomSheetDialog)
            val view=layoutInflater.inflate(R.layout.bottom_items, null)

            view.findViewById<TextView>(R.id.classNotes).setOnClickListener {
                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.classNotes).setTextColor(resources.getColor(R.color.colorPrimary))

                startActivity(Intent(this, ClassNotesActivity::class.java))

                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)

                //dialog.dismiss()

            }
            view.findViewById<TextView>(R.id.remainders).setOnClickListener {
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.remainders).setTextColor(resources.getColor(R.color.colorPrimary))

                startActivity(Intent(this, ReminderActivity::class.java))

                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)

                // dialog.dismiss()
            }
            view.findViewById<TextView>(R.id.profile).setOnClickListener {
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.profile).setTextColor(resources.getColor(R.color.colorPrimary))

                startActivity(Intent(this, StudentProfileActivity::class.java))

                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)

                //  dialog.dismiss()


            }
            view.findViewById<TextView>(R.id.rateUs).setOnClickListener {
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.rateUs).setTextColor(resources.getColor(R.color.colorPrimary))
                //startActivity(Intent(this, StudentDetailsActivity::class.java))

                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)

                //  dialog.dismiss()
            }
            view.findViewById<TextView>(R.id.logOut).setOnClickListener {

                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.logOut).setTextColor(resources.getColor(R.color.colorPrimary))

                // Logout the user from session
                AppPreferences.isLogin = false
                AppPreferences.studentID = ""
                AppPreferences.studentName = ""
                startActivity(Intent(this, AuthenticationActivity::class.java))

                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(0)
                //  dialog.dismiss()

            }
            view.findViewById<TextView>(R.id.collegeMates).setOnClickListener {
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.collegeMates).setTextColor(resources.getColor(R.color.colorPrimary))
                startActivity(Intent(this, ClassMatesActivity::class.java))

                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)

                //  dialog.dismiss()

            }


            dialog.setContentView(view)
            //dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()



        }

    }

    private fun retriveClassNotesData() {

        val myRef = FirebaseDatabase.getInstance().reference.child(AppPreferences.studentID).child("files_data")
        myRef.keepSynced(true)

        val classNotesListener = object :ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                classNotesList.clear()

                if(dataSnapshot.exists()) {

                    animationView.visibility = GONE
                    prefsLayout.visibility = VISIBLE
                    recyclerLayout.visibility = VISIBLE

                    for (ds in dataSnapshot.children) {

                        val classNotesData = ds.getValue(FileUploadData::class.java)

                        if (classNotesData != null) {
                            classNotesList.add(classNotesData)

                            classNotesAdapter = ClassNotesAdapter(
                                this@ClassNotesActivity,
                                classNotesList,
                                AppPreferences.studentName.toString()
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


                } else {
                    Toast.makeText(baseContext,"No Data Found !!!",Toast.LENGTH_LONG).show()
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