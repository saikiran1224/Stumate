package com.umang.stumate.general

import android.app.Dialog
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.umang.stumate.R
import com.umang.stumate.adapters.ClassMatesAdapter
import com.umang.stumate.auth.AuthenticationActivity
import com.umang.stumate.modals.StudentData
import com.umang.stumate.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_class_mates.*
import kotlinx.android.synthetic.main.activity_class_notes.closeButton

class ClassMatesActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var classMatesList: ArrayList<StudentData>
    lateinit var bottomnav: BottomAppBar

    lateinit var mGoogleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_mates)
        bottomnav=findViewById(R.id.bottomNavigation)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        AppPreferences.init(this)

        closeButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        classMatesList = ArrayList<StudentData>()

        // ClassNotesAdapter Layout Manager
        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        linearLayoutManager.reverseLayout = false

        uploadFilesButtonfab.setOnClickListener {
            val intent = Intent(this, UploadFilesActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }


        bottomnav.setOnClickListener {
            val dialog= BottomSheetDialog(this,R.style.BottomSheetDialog)
            val view=layoutInflater.inflate(R.layout.bottom_home, null)

            view.findViewById<TextView>(R.id.homePage).setOnClickListener {
                view.findViewById<TextView>(R.id.homePage).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.homePage).setTextColor(resources.getColor(R.color.colorPrimary))

                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                finish()
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
                finish()
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
                finish()
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
                finish()
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

                val intent = Intent(this, AboutActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                finish()
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
                        finish()                    }
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
                finish()
                view.findViewById<TextView>(R.id.homePage).setBackgroundResource(0)
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


        // Retrieving Data from Firebase Realtime Database
        retreiveClassNotesData()


    }

    private fun retreiveClassNotesData() {

        animationView.visibility = View.VISIBLE

        val myRef = FirebaseDatabase.getInstance().reference.child("students_data").orderByChild("studentID").equalTo(AppPreferences.studentID)

        val classMatesListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()) {

                    animationView.visibility = View.GONE
                    for (ds in dataSnapshot.children) {
                        val classMatesData = ds.getValue(StudentData::class.java)
                        if (classMatesData != null) {
                            classMatesList.add(classMatesData)
                        }
                    }
                    val classMatesAdapter = ClassMatesAdapter(baseContext,classMatesList)

                    linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                    linearLayoutManager.reverseLayout = true
                    linearLayoutManager.stackFromEnd = true
                    classMatesRecycler.layoutManager = linearLayoutManager

                    classMatesRecycler.setHasFixedSize(true)
                    classMatesRecycler.adapter = classMatesAdapter
                    classMatesAdapter.notifyDataSetChanged()

                } else {
                    animationView.visibility = View.GONE
                    noDataAnimation.visibility = View.VISIBLE
                }
            } override fun onCancelled(error: DatabaseError) {
                animationView.visibility = View.GONE
                noDataAnimation.visibility = View.VISIBLE
                Toast.makeText(baseContext,error.message, Toast.LENGTH_LONG).show()
            }
        }
        myRef.addValueEventListener(classMatesListener)

    }

}