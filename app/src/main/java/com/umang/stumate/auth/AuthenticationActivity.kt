package com.umang.stumate.auth

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.umang.stumate.general.HomeActivity
import com.umang.stumate.onboarding.GettingStartedActivity
import com.umang.stumate.R
import com.umang.stumate.modals.StudentData
import com.umang.stumate.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_authentication.*

class AuthenticationActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    lateinit var editEmailIn:TextInputEditText
    lateinit var editPasswordIn:TextInputEditText
    lateinit var backButton: ImageView
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        auth= FirebaseAuth.getInstance()

        //Initialising App Preferences Activity
        AppPreferences.init(this)

        editEmailIn=findViewById(R.id.editEmailIn)
        editPasswordIn=findViewById(R.id.editPasswordIn)
        backButton=findViewById(R.id.welcomeBackButton)
        val email=editEmailIn.text.toString()


       backButton.setOnClickListener{
            val intent = Intent(this, GettingStartedActivity::class.java)
            startActivity(intent)
        }

        // Changing the Sign in and Sign up Text here
        newUserText.setOnClickListener{
            val welcomeText: TextView = findViewById(R.id.welcomeText)
            val btnSignUp: Button = findViewById(R.id.btnName)
            val userText: TextView = findViewById(R.id.newUserText)
            val oAuthText: TextView = findViewById(R.id.singInText)

            // Checking the Text
            if(newUserText.text.equals("Don't have an Account? Sign Up")) {

                // We need to change it to Sign in here since new user Text is Sign Up
                welcomeText.text = "Create a new\nAccount"
                btnSignUp.text = "\t\t\tSign up\t\t\t"
                userText.text = "Already have an Account! Sign in"
                oAuthText.text = "Or Sign up with"
                /*startActivity(Intent(this,HomeActivity::class.java))
                finish()*/

            } else {

                // We need to change it to Sign up here since new user Text is Sign in
                welcomeText.text = "Here to Get\nWelcome"
                btnSignUp.text = "\t\t\tSign in\t\t\t"
                userText.text = "Don't have an Account? Sign Up"
                oAuthText.text = "Or Sign in with"
                /*startActivity(Intent(this,StudentDetailsActivity::class.java))
                finish()*/
            }
        }
        // When user clicks on Button, check the Text
        btnName.setOnClickListener {
            // Check Validation as written in StudentDetailsActivity.kt file from line 38
            if(isNullOrEmpty(editEmailIn.text)) {
                edtEmailID.error = "Please enter Emailid"
            } else if(isNullOrEmpty(editPasswordIn.text)) {
                edtEmailID.error = null
                edtPassword.error = "Please enter Password"
            } else {
                if(btnName.text.equals("\t\t\tSign in\t\t\t")) {
                    //Implement Sign in code here
                    auth.signInWithEmailAndPassword(editEmailIn.text.toString(), editPasswordIn.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                /* val user = auth.currentUser
                                 updateUI(user)*/
                                retrieveStudentDetails(editEmailIn.text.toString())

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(this, "Invalid Credentials",
                                    Toast.LENGTH_SHORT).show()
                                //updateUI(null)
                                // ...
                            }
                            // ...
                        }
                    // Send intent to Home Activity (Bottom Navigation Activity)
                } else {
                    // Implement Sign up code Here
                    auth.createUserWithEmailAndPassword(editEmailIn.text.toString(), editPasswordIn.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                val intent = Intent(this,StudentDetailsActivity::class.java)
                                intent.putExtra("Email",editEmailIn.text.toString() )
                                startActivity(intent)

                                finish()
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                                //updateUI(null)
                            }
                            // ...
                        }
                    // Send intent to Student Details Activity to collect Dept, Section
                    //TODO: Send EmailID though intent to StudentDetails Activity

                }

            }
        }
    }

    private fun retrieveStudentDetails(emailID: String) {

        val myRef = FirebaseDatabase.getInstance().reference
        val studentDataQuery = myRef.child("students_data").orderByChild("emailID").equalTo(emailID)
        val studentDataListener = object :ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val studentData = ds.getValue(StudentData::class.java)
                    if (studentData != null) {
                        // Setting Shared Preferences of User
                        AppPreferences.isLogin = true
                        AppPreferences.studentName = studentData.studentName.toString()
                        AppPreferences.studentID = studentData.studentID.toString()

                        sendIntent()
                    }

                }


            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@AuthenticationActivity,
                    error.message,
                    Toast.LENGTH_LONG
                ).show()

            }
        }
        studentDataQuery.addListenerForSingleValueEvent(studentDataListener)
    }


    private fun sendIntent() {

        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

        private fun isNullOrEmpty(str: Editable?): Boolean {
            if (str != null && !str.trim().isEmpty())
                return false
            return true
        }
}