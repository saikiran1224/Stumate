package com.umang.stumate

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.umang.stumate.general.HomeActivity

class TeamActivity : AppCompatActivity() {
    lateinit var backgroundImageLayout: NestedScrollView
    lateinit var teamPhoto: LinearLayout
   lateinit var imgGithub: ImageView
    lateinit var imgLinkedin:android.widget.ImageView
    lateinit var imgInstagram:android.widget.ImageView
    lateinit var backButton:android.widget.ImageView
    lateinit var txtName: TextView
    lateinit var txtRoleName:TextView
    lateinit var txtYearDept:TextView
    lateinit var txtInterests:TextView
   lateinit var txtEmailID:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)
        backgroundImageLayout = findViewById(R.id.backgroundImageLayout)

        backButton = findViewById(R.id.back_button)

        imgGithub = findViewById(R.id.imageGithub)
        imgLinkedin = findViewById(R.id.imageLinkedin)
        imgInstagram = findViewById(R.id.imageInstagram)

        teamPhoto = findViewById(R.id.imageTeamPhoto)

        txtName = findViewById(R.id.txtName)
        txtRoleName = findViewById(R.id.txtRoleName)
        txtYearDept = findViewById(R.id.txtYearDept)
        txtInterests = findViewById(R.id.txtInterests)
        txtEmailID = findViewById(R.id.txtEmailID)

        val bundle = intent.extras!!
        val name = bundle.getString("name")
        val roleName = bundle.getString("roleName")
        val teamPhotoName = bundle.getInt("imageTeamPhoto")
        val yearDept = bundle.getString("yearDept")
        val interests = bundle.getString("interests")
        val emailID = bundle.getString("emailID")

        assert(roleName != null)
        when (roleName) {
            "Android Developer" -> {
                backgroundImageLayout.setBackgroundResource(R.drawable.blue_background)
                imgGithub.setImageTintList(ColorStateList.valueOf(resources.getColor(R.color.colorPrimary)))
                imgInstagram.setImageTintList(ColorStateList.valueOf(resources.getColor(R.color.colorPrimary)))
                imgLinkedin.setImageTintList(ColorStateList.valueOf(resources.getColor(R.color.colorPrimary)))
                backButton.setImageTintList(ColorStateList.valueOf(resources.getColor(R.color.colorPrimary)))
            }
            else -> {
                backgroundImageLayout.setBackgroundResource(R.drawable.blue_background)
                imgGithub.setImageTintList(ColorStateList.valueOf(resources.getColor(R.color.colorRed)))
                imgInstagram.setImageTintList(ColorStateList.valueOf(resources.getColor(R.color.colorRed)))
                imgLinkedin.setImageTintList(ColorStateList.valueOf(resources.getColor(R.color.colorRed)))
                backButton.setImageTintList(ColorStateList.valueOf(resources.getColor(R.color.colorRed)))
            }
        }

        txtName.setText(name)
        txtRoleName.setText(roleName)
        txtYearDept.setText(yearDept)
        txtEmailID.setText(emailID)
        txtInterests.setText(interests)

        teamPhoto.setBackgroundResource(teamPhotoName)

        backButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@TeamActivity, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        })
    }
}