package com.umang.stumate.general

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Color.TRANSPARENT
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.collection.LLRBNode
import com.umang.stumate.R
import com.umang.stumate.adapters.DashboardIconsAdapter
import com.umang.stumate.adapters.NewsAdapter
import com.umang.stumate.auth.AuthenticationActivity
import com.umang.stumate.auth.StudentDetailsActivity
import com.umang.stumate.modals.DashboardIconData
import com.umang.stumate.modals.NewsData
import com.umang.stumate.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var newsLayoutManager: LinearLayoutManager
    lateinit var bottomnav: BottomAppBar
    @SuppressLint("SetTextI18n", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        bottomnav=findViewById(R.id.bottomnav)
        AppPreferences.init(this)

        if(AppPreferences.isLogin) {
            studentName.text = "Hi " + AppPreferences.studentName
        }

        uploadFilesButton.setOnClickListener {
            startActivity(Intent(this, UploadFilesActivity::class.java))
        }

        // DashboardIconsAdapter
        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        linearLayoutManager.reverseLayout = false

        val dashboardIconsList = ArrayList<DashboardIconData>()
        dashboardIconsList.add(
            DashboardIconData(
                "Class Notes",
                R.drawable.ic_baseline_menu_book_24
            )
        )
        dashboardIconsList.add(
            DashboardIconData(
                "Question Papers",
                R.drawable.ic_baseline_find_in_page_24
            )
        )
        dashboardIconsList.add(
            DashboardIconData(
                "Reminders",
                R.drawable.ic_baseline_notifications_active_24
            )
        )

        val dashboardIconAdapter = DashboardIconsAdapter(dashboardIconsList)
        dashboardRecycler.layoutManager = linearLayoutManager
        dashboardRecycler.adapter = dashboardIconAdapter


        // News Adapter
        newsLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val newsList = ArrayList<NewsData>()
        newsList.add(
            NewsData(
                R.drawable.student,
                "New Version is Live!",
                "New UI and added facility to set Timely Reminders for Assignments"
            )
        )
        newsList.add(
            NewsData(
                R.drawable.stumate,
                "Welcome to Stumate",
                "Hope you are enjoying the App and All the Best for your Exams"
            )
        )

        val newsAdapter = NewsAdapter(newsList)
        newsRecycler.layoutManager = newsLayoutManager
        newsRecycler.adapter = newsAdapter

        bottomnav.setOnClickListener {
            val dialog= BottomSheetDialog(this,R.style.BottomSheetDialog)
            val view=layoutInflater.inflate(R.layout.bottom_items, null)

            view.findViewById<TextView>(R.id.classNotes).setOnClickListener {
                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.classNotes).setTextColor(resources.getColor(R.color.colorPrimary))



                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)



                startActivity(Intent(this, ClassNotesActivity::class.java))

            }
            view.findViewById<TextView>(R.id.remainders).setOnClickListener {
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.remainders).setTextColor(resources.getColor(R.color.colorPrimary))
               // startActivity(Intent(this, StudentDetailsActivity::class.java))

                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)



            }
            view.findViewById<TextView>(R.id.profile).setOnClickListener {
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.profile).setTextColor(resources.getColor(R.color.colorPrimary))
                //startActivity(Intent(this, StudentDetailsActivity::class.java))


            }
            view.findViewById<TextView>(R.id.rateUs).setOnClickListener {
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.rateUs).setTextColor(resources.getColor(R.color.colorPrimary))
                //startActivity(Intent(this, StudentDetailsActivity::class.java))





            }
            view.findViewById<TextView>(R.id.logOut).setOnClickListener {

                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.logOut).setTextColor(resources.getColor(R.color.colorPrimary))

                // Logout the user from session
                AppPreferences.isLogin = false
                AppPreferences.studentID = ""
                AppPreferences.studentName = ""
                startActivity(Intent(this, AuthenticationActivity::class.java))
            }
            view.findViewById<TextView>(R.id.collegeMates).setOnClickListener {
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.collegeMates).setTextColor(resources.getColor(R.color.colorPrimary))
              //  startActivity(Intent(this, StudentDetailsActivity::class.java))
            }


            dialog.setContentView(view)
            //dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()



        }
    }
}