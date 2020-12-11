package com.umang.stumate.general

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
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
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.activity_class_notes.*
import kotlinx.android.synthetic.main.activity_class_notes.adView
import kotlinx.android.synthetic.main.activity_class_notes.closeButton
import kotlinx.android.synthetic.main.activity_class_notes.divider
import kotlinx.android.synthetic.main.activity_class_notes.searchEditText


class ClassNotesActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var classNotesList: ArrayList<FileUploadData>
    private lateinit var classNotesAdapter: ClassNotesAdapter

    lateinit var mGoogleSignInClient: GoogleSignInClient



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_notes)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        AppPreferences.init(this)

        // Google AdMob
        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)


        closeButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            
        }

        classNotesList = ArrayList<FileUploadData>()

        // ClassNotesAdapter Layout Manager
        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        linearLayoutManager.reverseLayout = true


        sortbyDate.setOnClickListener {
            Toast.makeText(this, "Already Sorted by Date!", Toast.LENGTH_LONG).show()
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
            val intent = Intent(this, UploadFilesActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            
        }


        classNotesBottomNav.setOnClickListener {
            val dialog= BottomSheetDialog(this,R.style.BottomSheetDialog)
            val view=layoutInflater.inflate(R.layout.bottom_home, null)

            view.findViewById<TextView>(R.id.homePage).setOnClickListener {
                view.findViewById<TextView>(R.id.homePage).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.homePage).setTextColor(resources.getColor(R.color.colorPrimary))

                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                
                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)

                //dialog.dismiss()

            }

            view.findViewById<TextView>(R.id.classNotes).setOnClickListener {
                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.classNotes).setTextColor(resources.getColor(R.color.colorPrimary))

                val intent = Intent(this, ClassNotesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                
                view.findViewById<TextView>(R.id.homePage).setBackgroundResource(0)
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

                val intent = Intent(this, ReminderActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                
                view.findViewById<TextView>(R.id.homePage).setBackgroundResource(0)
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

                val intent = Intent(this, StudentProfileActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                
                view.findViewById<TextView>(R.id.homePage).setBackgroundResource(0)
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

                val intent = Intent(this, AboutActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                
                view.findViewById<TextView>(R.id.homePage).setBackgroundResource(0)
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

                val logoutDialog = Dialog(this)
                logoutDialog.setContentView(R.layout.logout_dialog)
                logoutDialog.setCancelable(false)
                logoutDialog.setCanceledOnTouchOutside(false)
                logoutDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)

                logoutDialog.findViewById<Button>(R.id.btnLogout).setOnClickListener {
                    val account = GoogleSignIn.getLastSignedInAccount(this)
                    if (account != null) {
                        //Some one is already logged in
                        // Google sign out
                        // Google sign out
                        mGoogleSignInClient.signOut().addOnCompleteListener(this) {
                            // Logout the user from session
                            AppPreferences.isLogin = false
                            AppPreferences.studentID = ""
                            AppPreferences.studentName = ""
                            val intent = Intent(this, AuthenticationActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                            startActivity(intent)
                            finish()
                                                    }
                    } else {
                        // Logout the user from session
                        AppPreferences.isLogin = false
                        AppPreferences.studentID = ""
                        AppPreferences.studentName = ""
                        val intent = Intent(this, AuthenticationActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        startActivity(intent)
                        finish()
                                            }
                }

                logoutDialog.findViewById<Button>(R.id.btnCancel).setOnClickListener {
                    logoutDialog.dismiss()
                }

                logoutDialog.show()
                view.findViewById<TextView>(R.id.homePage).setBackgroundResource(0)
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
                val intent = Intent(this, ClassMatesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                
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

                            classNotesAdapter = ClassNotesAdapter(this@ClassNotesActivity, classNotesList, AppPreferences.studentName.toString(), AppPreferences.studentID.toString())

                            classNotesRecycler.setHasFixedSize(true)
                            linearLayoutManager.reverseLayout = true
                            linearLayoutManager.stackFromEnd = true
                            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

                            classNotesRecycler.layoutManager = linearLayoutManager
                            classNotesRecycler.adapter = classNotesAdapter
                            classNotesAdapter.notifyDataSetChanged()

                        }

                    }




                } else {
                    animationView.visibility = GONE
                    prefsLayout.visibility = GONE
                    recyclerLayout.visibility = GONE

                    noDataAnimation.visibility = View.VISIBLE
                    Toast.makeText(baseContext,"No Data Found !!!",Toast.LENGTH_LONG).show()
                }


            } override fun onCancelled(error: DatabaseError) {

                animationView.visibility = GONE
                prefsLayout.visibility = GONE
                recyclerLayout.visibility = GONE

                noDataAnimation.visibility = View.VISIBLE
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