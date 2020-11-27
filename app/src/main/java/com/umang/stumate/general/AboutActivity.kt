package com.umang.stumate.general

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.umang.stumate.R
import com.umang.stumate.modals.FeedbackData
import com.umang.stumate.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_about.*


class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        AppPreferences.init(this)

        closeButton.setOnClickListener{
            val intent = Intent(this,HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }


        btnSubmit.setOnClickListener {
            if(editFeedback.text.toString().isEmpty()){
                edtFeedback.error = "Please enter Feedback"
            } else {
                edtFeedback.error = null

                val myRef = FirebaseDatabase.getInstance().getReference().child("feedback_data")
                myRef.push().setValue(FeedbackData(AppPreferences.studentName,AppPreferences.studentEmailID,editFeedback.text.toString().trim()))

                showToast("Feedback sent Successfully !")

                val intent = Intent(this,HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }

    }

    private fun showToast(message: String?) {
        Toast.makeText(this,message.toString(),Toast.LENGTH_SHORT).show()

    }
}