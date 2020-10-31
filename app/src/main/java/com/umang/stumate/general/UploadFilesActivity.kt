package com.umang.stumate.general

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.umang.stumate.R
import kotlinx.android.synthetic.main.activity_upload_files.*

class UploadFilesActivity : AppCompatActivity() {

    private lateinit var closeButton: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_files)

        closeButton = findViewById(R.id.closeButton)
        closeButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }


    }
}