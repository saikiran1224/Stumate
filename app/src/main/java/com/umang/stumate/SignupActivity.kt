package com.umang.stumate

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {
    lateinit var editEmailUp:TextInputEditText
    lateinit var editPasswordUp:TextInputEditText
    lateinit var editConformPasswordUp:TextInputEditText
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        editEmailUp=findViewById(R.id.editEmail)
        editPasswordUp=findViewById(R.id.editPassword)
        editConformPasswordUp=findViewById(R.id.editConformPassword)
        val btnGoogleUp:ImageView=findViewById(R.id.btnGoogleUp)
        val btnFacebookUp:ImageView=findViewById(R.id.btnFacebookUp)
        val btnTwitterUp:ImageView=findViewById(R.id.btnTwitterUp)
        val btnSignUp : Button = findViewById(R.id.btnSignUp)
        auth = FirebaseAuth.getInstance()
        btnSignUp.setOnClickListener {
            SignUpFlow()
        }
        btnGoogleUp.setOnClickListener {
            launchSignUpFlow()
        }
        btnFacebookUp.setOnClickListener {
            launchSignUpFlow()
        }
        btnTwitterUp.setOnClickListener {
            launchSignUpFlow()
        }

    }

    private fun SignUpFlow() {

        if(editEmailUp.text.toString().isNotEmpty() && editPasswordUp.text.toString().isNotEmpty() && editConformPasswordUp.text.toString().isNotEmpty()){
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
        if(editEmailUp.text.toString().isEmpty()){
            editEmailUp.error="Please Enter EmailId"
            editEmailUp.requestFocus()
            return
        }
        if(editPasswordUp.text.toString().isEmpty()){
            editPasswordUp.error="Please Enter Password"
            editPasswordUp.requestFocus()
            return
        }
        if(editConformPasswordUp.text.toString().isEmpty()){
            editConformPasswordUp.error="Please Enter Conform Password"
            editConformPasswordUp.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(editEmailUp.text.toString()).matches()){
            editEmailUp.error="Please Enter Valid EmailId"
            editEmailUp.requestFocus()
            return
        }
        auth.createUserWithEmailAndPassword(editEmailUp.text.toString(), editPasswordUp.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    startActivity(Intent(this,SignInActivity::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }

                // ...
            }
    }

    private fun launchSignUpFlow() {
        TODO("Not yet implemented")
    }
}