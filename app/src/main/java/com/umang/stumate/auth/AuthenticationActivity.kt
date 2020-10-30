package com.umang.stumate.auth

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Patterns
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.umang.stumate.HomeActivity
import com.umang.stumate.onboarding.GettingStartedActivity
import com.umang.stumate.R
import kotlinx.android.synthetic.main.activity_authentication.*
import kotlinx.android.synthetic.main.activity_student_details.*

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
        editEmailIn=findViewById(R.id.editEmailIn)
        editPasswordIn=findViewById(R.id.editPasswordIn)
        backButton=findViewById(R.id.welcomeBackButton)
        val email=editEmailIn.text.toString()
        val intent=Intent(this,StudentDetailsActivity::class.java)
        intent.putExtra("Email",email)
        startActivity(intent)
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
                editEmailIn.error = "Please enter Emailid"
            } else if(isNullOrEmpty(editPasswordIn.text)) {
                editPasswordIn.error = null
                edtPhone.error = "Please enter Password"
            }
            if(btnName.text.equals("\t\t\tSign in\t\t\t")) {
                //Implement Sign in code here
                auth.signInWithEmailAndPassword(editEmailIn.text.toString(), editPasswordIn.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                           /* val user = auth.currentUser
                            updateUI(user)*/
                            startActivity(Intent(this,StudentDetailsActivity::class.java))
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(this, "Invalid Credentials",
                                Toast.LENGTH_SHORT).show()
                            //updateUI(null)
                            // ...
                        }
                        // ...
                    }
                Toast.makeText(this, "User clicked on Sign in Button, Want to login", Toast.LENGTH_SHORT).show()
                // Send intent to Home Activity (Bottom Navigation Activity)
            } else {
                // Implement Sign up code Here
                auth.createUserWithEmailAndPassword(editEmailIn.text.toString(), editPasswordIn.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            startActivity(Intent(this,StudentDetailsActivity::class.java))
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                            //updateUI(null)
                        }
                        // ...
                    }
                Toast.makeText(this, "User clicked on Sign up Button", Toast.LENGTH_SHORT).show()
                // Send intent to Student Details Activity to collect Dept, Section
                //TODO: Send EmailID though intent to StudentDetails Activity
                
            }
        }
    }
        private fun isNullOrEmpty(str: Editable?): Boolean {
            if (str != null && !str.trim().isEmpty())
                return false
            return true
        }
/*
    private fun doLogin() {
        if(editEmailIn.text.toString().isNotEmpty() && editPasswordIn.text.toString().isNotEmpty()){
            startActivity(Intent(this, AuthenticationActivity::class.java))
            finish()
        }
        if(editEmailIn.text.toString().isEmpty()){
            editEmailIn.error="Please Enter EmailId"
            editEmailIn.requestFocus()
            return
        }
        if(editPasswordIn.text.toString().isEmpty()){
            editPasswordIn.error="Please Enter Password"
            editPasswordIn.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(editEmailIn.text.toString()).matches()){
            editEmailIn.error="Please Enter Valid EmailId"
            editEmailIn.requestFocus()
            return
        }
        auth.signInWithEmailAndPassword(editEmailIn.text.toString(), editPasswordIn.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Login failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                    // ...
                }

                // ...
            }
    }
*/

/*
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
*/

/*
    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser!=null){
            startActivity(Intent(this, StudentDetailsActivity::class.java))
            finish()
        }
    }
*/
}