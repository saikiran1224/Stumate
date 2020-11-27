package com.umang.stumate.general

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.umang.stumate.R
import com.umang.stumate.TeamActivity
import kotlinx.android.synthetic.main.activity_about.*


class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        btnSubmit.setOnClickListener {
            if(editFeedback.text.toString().isEmpty()){
                Toast.makeText(this,"Please Provide your feedback",Toast.LENGTH_SHORT).show()
            }
        }
        txtViewMore.setOnClickListener {
            startActivity(Intent(this,TeamActivity::class.java))
        }
        txtViewMore2.setOnClickListener {
            startActivity(Intent(this,TeamActivity::class.java))
        }

    }
}