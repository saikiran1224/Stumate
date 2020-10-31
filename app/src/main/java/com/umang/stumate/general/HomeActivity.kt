package com.umang.stumate.general

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.umang.stumate.R
import com.umang.stumate.adapters.DashboardIconsAdapter
import com.umang.stumate.adapters.NewsAdapter
import com.umang.stumate.modals.DashboardIconData
import com.umang.stumate.modals.NewsData
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var newsLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        uploadFilesButton.setOnClickListener {
            startActivity(Intent(this, UploadFilesActivity::class.java))
        }

        // DashboardIconsAdapter
        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        linearLayoutManager.reverseLayout = false

        val dashboardIconsList = ArrayList<DashboardIconData>()
        dashboardIconsList.add(DashboardIconData("Class Notes", R.drawable.ic_baseline_menu_book_24))
        dashboardIconsList.add(DashboardIconData("Question Papers",
            R.drawable.ic_baseline_find_in_page_24
        ))
        dashboardIconsList.add(DashboardIconData("Reminders",
            R.drawable.ic_baseline_notifications_active_24
        ))

        val dashboardIconAdapter = DashboardIconsAdapter(dashboardIconsList)
        dashboardRecycler.layoutManager = linearLayoutManager
        dashboardRecycler.adapter = dashboardIconAdapter


        // News Adapter
        newsLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        val newsList = ArrayList<NewsData>()
        newsList.add(NewsData(R.drawable.student,"New Version is Live!","New UI and added facility to set Timely Reminders for Assignments"))
        newsList.add(NewsData(R.drawable.stumate,"Welcome to Stumate","Hope you are enjoying the App and All the Best for your Exams"))

        val newsAdapter = NewsAdapter(newsList)
        newsRecycler.layoutManager = newsLayoutManager
        newsRecycler.adapter = newsAdapter






    }
}