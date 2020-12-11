package com.umang.stumate.general

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.*
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.umang.stumate.R
import com.umang.stumate.adapters.DashboardIconsAdapter
import com.umang.stumate.adapters.NewsAdapter
import com.umang.stumate.auth.AuthenticationActivity
import com.umang.stumate.modals.DashboardIconData
import com.umang.stumate.modals.NewsData
import com.umang.stumate.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var newsLayoutManager: LinearLayoutManager
    lateinit var bottomnav: BottomAppBar

    lateinit var mGoogleSignInClient: GoogleSignInClient


    @SuppressLint("SetTextI18n", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        bottomnav=findViewById(R.id.bottomnav)
        AppPreferences.init(this)

        // Google AdMob
        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)



        if(AppPreferences.isLogin) {
            studentName.text = "Hi " + AppPreferences.studentName
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        uploadFilesButton.setOnClickListener {
            val intent = Intent(this, UploadFilesActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
                    }

        notificationLayout.setOnClickListener {
            val intent = Intent(this, ViewNotificationsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
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
                "Class Mates",
                R.drawable.ic_baseline_supervisor_account_24
            )
        )
        dashboardIconsList.add(
            DashboardIconData(
                "Reminders",
                R.drawable.ic_baseline_notifications_active_24
            )
        )
        dashboardIconsList.add(DashboardIconData("Profile", R.drawable.ic_baseline_person_24))

        val dashboardIconAdapter = DashboardIconsAdapter(dashboardIconsList)
        dashboardRecycler.layoutManager = linearLayoutManager
        dashboardRecycler.adapter = dashboardIconAdapter

        searchEditText.setOnClickListener {
            val intent = Intent(this, ClassNotesActivity::class.java)
            intent.putExtra("navigatedFrom", "HomeActivity")
            startActivity(intent)
        }

        // News Adapter
        newsLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val newsList = ArrayList<NewsData>()
        newsList.add(
            NewsData(
                R.drawable.student,
                "New Version is Live!",
                "New UI and added facility to send Reminders for Assignments,Exams,etc."
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
            val dialog= BottomSheetDialog(this, R.style.BottomSheetDialog)
            val view=layoutInflater.inflate(R.layout.bottom_items, null)


            view.findViewById<TextView>(R.id.classNotes).setOnClickListener {
                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.classNotes).setTextColor(resources.getColor(R.color.colorPrimary))


                val intent = Intent(this, ClassNotesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)

                //dialog.dismiss()

            }
            view.findViewById<TextView>(R.id.remainders).setOnClickListener {
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.remainders).setTextColor(resources.getColor(R.color.colorPrimary))

                val intent = Intent(this, ReminderActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                

                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)

               // dialog.dismiss()
            }
            view.findViewById<TextView>(R.id.profile).setOnClickListener {

                view.findViewById<TextView>(R.id.profile).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.profile).setTextColor(resources.getColor(R.color.colorPrimary))
                val intent = Intent(this, StudentProfileActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                
                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)
                
            }
            view.findViewById<TextView>(R.id.rateUs).setOnClickListener {
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.rateUs).setTextColor(resources.getColor(R.color.colorPrimary))

                val intent = Intent(this, AboutActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                
                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)

              //  dialog.dismiss()
            }
            view.findViewById<TextView>(R.id.logOut).setOnClickListener {

                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.logOut).setTextColor(resources.getColor(R.color.colorPrimary))

                val logoutDialog = Dialog(this)
                logoutDialog.setContentView(R.layout.logout_dialog)
                logoutDialog.setCancelable(false)
                logoutDialog.setCanceledOnTouchOutside(false)
                logoutDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)

                logoutDialog.findViewById<Button>(R.id.btnLogout).setOnClickListener {
                    val account = GoogleSignIn.getLastSignedInAccount(this)
                    if (account != null) {
                        //Some one is already logged in
                        // Google sign out
                        // Google sign out
                        mGoogleSignInClient.signOut().addOnCompleteListener(this) {
                            // Logout the user from session
                            AppPreferences.isLogin = false
                            AppPreferences.studentID = ""
                            AppPreferences.studentName = ""
                            val intent = Intent(this, AuthenticationActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                            startActivity(intent)
                            finish()
                                                    }
                    } else {
                        // Logout the user from session
                        AppPreferences.isLogin = false
                        AppPreferences.studentID = ""
                        AppPreferences.studentName = ""
                        val intent = Intent(this, AuthenticationActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        startActivity(intent)
                        finish()
                                            }
                }

                logoutDialog.findViewById<Button>(R.id.btnCancel).setOnClickListener {
                    logoutDialog.dismiss()
                }

                logoutDialog.show()

                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)

              //  dialog.dismiss()
            }



            view.findViewById<TextView>(R.id.collegeMates).setOnClickListener {
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.collegeMates).setTextColor(resources.getColor(R.color.colorPrimary))

                val intent = Intent(this, ClassMatesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                
                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)

              //  dialog.dismiss()

            }


            dialog.setContentView(view)
            //dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()

        }

    }
}