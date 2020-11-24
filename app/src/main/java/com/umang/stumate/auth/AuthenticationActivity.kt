package com.umang.stumate.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import com.umang.stumate.R
import com.umang.stumate.general.HomeActivity
import com.umang.stumate.general.StudentProfileActivity
import com.umang.stumate.modals.StudentData
import com.umang.stumate.onboarding.GettingStartedActivity
import com.umang.stumate.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_authentication.*
import kotlinx.android.synthetic.main.progress_bar.*


class AuthenticationActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val RC_SIGN_IN = 123
    lateinit var editEmailIn:TextInputEditText
    lateinit var editPasswordIn:TextInputEditText
    lateinit var backButton: ImageView

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser!=null){
            val intent=Intent(applicationContext, StudentDetailsActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n", "RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        createRequest()
        auth= FirebaseAuth.getInstance()

        //Initialising App Preferences Activity
        AppPreferences.init(this)


        editEmailIn=findViewById(R.id.editEmailIn)
        editPasswordIn=findViewById(R.id.editPasswordIn)
        backButton=findViewById(R.id.welcomeBackButton)
        val email=editEmailIn.text.toString()
        //mail data
        val acc = GoogleSignIn.getLastSignedInAccount(this)
        if (acc != null) {
            val personEmail = acc.email
            val intent = Intent(this@AuthenticationActivity, StudentProfileActivity::class.java)
            intent.putExtra("Email", personEmail)
            startActivity(intent)
        }

        googleSign.setOnClickListener {
            signIn()
        }

       backButton.setOnClickListener{
            val intent = Intent(this, GettingStartedActivity::class.java)
            startActivity(intent)
        }
        //This is for the google account mailId and names
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            val personName = acct.displayName
            val personEmail = acct.email
            val personId = acct.id
        }
        // Changing the Sign in and Sign up Text here
        llAccount.setOnClickListener{
            val welcomeText: TextView = findViewById(R.id.welcomeText)
            val btnSignUp: Button = findViewById(R.id.btnName)
            val userText: TextView = findViewById(R.id.newUserText)
            val userTextSign:TextView=findViewById(R.id.newUserTextSign)
            val oAuthText: TextView = findViewById(R.id.singInText)

            // Checking the Text
            if(newUserText.equals("Don't have an Account?")) {

                // We need to change it to Sign in here since new user Text is Sign Up

                welcomeText.text = "Here to Get\nWelcome"
                btnSignUp.text = "\t\t\tSign in\t\t\t"
                userText.text = "Don't have an Account? "
                userTextSign.text="Sign Up"
                oAuthText.text = "Or Sign in with"
                /*startActivity(Intent(this,StudentDetailsActivity::class.java))
                finish()*/


            } else {

                // We need to change it to Sign up here since new user Text is Sign in
                welcomeText.text = "Create a new\nAccount"
                btnSignUp.text = "\t\t\tSign up\t\t\t"
                userText.text = "Already have an Account! "
                userTextSign.text="Sign in"
                oAuthText.text = "Or Sign up with"
                /*startActivity(Intent(this,HomeActivity::class.java))
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

                edtPassword.error = null
                loadingProgress.visibility = View.VISIBLE

                if(btnName.text.equals("\t\t\tSign in\t\t\t")) {

                    //Implement Sign in code here
                    auth.signInWithEmailAndPassword(
                        editEmailIn.text.toString(),
                        editPasswordIn.text.toString()
                    )
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                /* val user = auth.currentUser
                                 updateUI(user)*/
                                retrieveStudentDetails(editEmailIn.text.toString())

                            } else {
                                // If sign in fails, display a message to the user.
                                loadingProgress.visibility = View.GONE
                                Toast.makeText(
                                    this, "Invalid Credentials",
                                    Toast.LENGTH_SHORT
                                ).show()
                                //updateUI(null)
                                // ...
                            }
                            // ...
                        }
                    // Send intent to Home Activity (Bottom Navigation Activity)
                } else {
                    // Implement Sign up code Here
                    auth.createUserWithEmailAndPassword(
                        editEmailIn.text.toString(),
                        editPasswordIn.text.toString()
                    )
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                val intent = Intent(this, StudentDetailsActivity::class.java)
                                intent.putExtra("Email", editEmailIn.text.toString())
                                startActivity(intent)


                                loadingProgress.visibility = View.GONE


                                finish()
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(
                                    this, "Authentication failed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                //updateUI(null)

                                loadingProgress.visibility = View.GONE

                            }
                            // ...
                        }
                    // Send intent to Student Details Activity to collect Dept, Section
                    //TODO: Send EmailID though intent to StudentDetails Activity

                }

            }
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                // ...
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    val intent=Intent(applicationContext, StudentDetailsActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    // ...
                    Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }
                // ...
            }
    }

    private fun createRequest() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
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
                        AppPreferences.studentEmailID = studentData.emailID.toString()

                        // Subscribing Student to his Class Topic
                        FirebaseMessaging.getInstance().subscribeToTopic("/topics/" + AppPreferences.studentID)

                        sendIntent()

                        loadingProgress.visibility = View.GONE

                    }

                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@AuthenticationActivity,
                    error.message,
                    Toast.LENGTH_LONG
                ).show()

                loadingProgress.visibility = View.GONE


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