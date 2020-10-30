package com.umang.stumate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class SignInActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    lateinit var editEmailIn:TextInputEditText
    lateinit var editPasswordIn:TextInputEditText
    lateinit var backButton: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        auth= FirebaseAuth.getInstance()
        editEmailIn=findViewById(R.id.editEmailIn)
        editPasswordIn=findViewById(R.id.editPasswordIn)
        backButton=findViewById(R.id.welcomeBackButton)
       backButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnSignIn.setOnClickListener {
            doLogin()
            /*Toast.makeText(this, "Successfully Signed In !!!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)*/
        }
    }

    private fun doLogin() {
        if(editEmailIn.text.toString().isNotEmpty() && editPasswordIn.text.toString().isNotEmpty()){
            startActivity(Intent(this, SignInActivity::class.java))
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

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser!=null){
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
    }
}