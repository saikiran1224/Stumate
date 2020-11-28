package com.umang.stumate.general

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.FirebaseDatabase
import com.umang.stumate.R
import com.umang.stumate.auth.AuthenticationActivity
import com.umang.stumate.modals.NotificationData
import com.umang.stumate.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_class_notes.*
import kotlinx.android.synthetic.main.activity_reminder.*
import kotlinx.android.synthetic.main.activity_reminder.closeButton
import org.json.JSONObject
import java.util.*
import kotlin.collections.HashMap


class ReminderActivity : AppCompatActivity() {

    private val TAG = "TOKENS_DATA"
    private val FCM_API = "https://fcm.googleapis.com/fcm/send"
    private val serverKey = "key=" + AppPreferences.AUTH_KEY_FCM
    private val contentType = "application/json"
    var TOPIC: String? = null
    lateinit var mGoogleSignInClient: GoogleSignInClient



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        setUpTypeList()
        AppPreferences.init(this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        closeButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
                    }


        remainderFAB.setOnClickListener {
            val intent = Intent(this, UploadFilesActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
                    }

        remainderBottomNav.setOnClickListener {
            val dialog= BottomSheetDialog(this, R.style.BottomSheetDialog)
            val view=layoutInflater.inflate(R.layout.bottom_home, null)

            view.findViewById<TextView>(R.id.homePage).setOnClickListener {
                view.findViewById<TextView>(R.id.homePage).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.homePage).setTextColor(resources.getColor(R.color.colorPrimary))

                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                
                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)

                //dialog.dismiss()

            }

            view.findViewById<TextView>(R.id.classNotes).setOnClickListener {
                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.classNotes).setTextColor(resources.getColor(R.color.colorPrimary))

                val intent = Intent(this, ClassNotesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                
                view.findViewById<TextView>(R.id.homePage).setBackgroundResource(0)
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
                
                view.findViewById<TextView>(R.id.homePage).setBackgroundResource(0)
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
                

                view.findViewById<TextView>(R.id.homePage).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)

                //  dialog.dismiss()


            }
            view.findViewById<TextView>(R.id.rateUs).setOnClickListener {
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.rateUs).setTextColor(resources.getColor(R.color.colorPrimary))
                //startActivity(Intent(this, StudentDetailsActivity::class.java))

                val intent = Intent(this, AboutActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                
                view.findViewById<TextView>(R.id.homePage).setBackgroundResource(0)
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
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(0)
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





        btnName.setOnClickListener {

            //var message = editMessage.text.toString()

          //  Toast.makeText(baseContext, " " + message, Toast.LENGTH_SHORT).show()
            val title = editTitleMessage.text.toString()
            val type = chooseType.text.toString()
            val description = editDescription.text.toString()

            if(isNullOrEmpty(title.toString())) {
                edtTitleMessage.error = "Please enter Title of Notification"
            } else if (isNullOrEmpty(type.toString())) {
                edtType.error = "Please choose Relevant Type"
                edtTitleMessage.error = null
            } else if(isNullOrEmpty(description.toString())) {
                edtDescription.error = "Please enter Description"
                edtType.error = null
            } else {

                btnName.isEnabled = false

                edtDescription.error = null

                try {
                    val queue = Volley.newRequestQueue(this)
                    val url = "https://fcm.googleapis.com/fcm/send"


                    TOPIC = "/topics/"+AppPreferences.studentID

                    val data = JSONObject()
                    data.put("title",  title + " from "+AppPreferences.studentName )
                    data.put("message", description + " Related to " + type)

                    // Can be used for Scheduled Notifications
/*
                    if(isNullOrEmpty(date) and isNullOrEmpty(time)) {

                      //  data.put("isScheduled", "false")

                        Toast.makeText(this, "The message is not scheduled", Toast.LENGTH_LONG).show()
                    } else {
                        data.put("title", title + " regarding " + type)
                        data.put(
                            "message",
                            description + "\n Sent by " + AppPreferences.studentName
                        )
                        data.put("isScheduled", "true")
                        data.put("scheduledTime", date + " " + time)

                        Toast.makeText(
                            this,
                            "Message is scheduled for " + date + " " + time + " " + description,
                            Toast.LENGTH_LONG
                        ).show()
                    }
*/

                    Log.e(TAG, "" + data)

                    val notification_data = JSONObject()
                    notification_data.put("data", data)
                    notification_data.put("to", TOPIC)

                    Log.e(TAG, "" + notification_data)
                    val request: JsonObjectRequest = object :
                        JsonObjectRequest(url, notification_data, object :
                            Response.Listener<JSONObject?> {
                            override fun onResponse(response: JSONObject?) {

                                val myRef = FirebaseDatabase.getInstance().getReference(AppPreferences.studentID).child("notifications_data")
                                myRef.push().setValue(NotificationData(title,description, AppPreferences.studentName))

                                val successDialog = Dialog(this@ReminderActivity)
                                successDialog.setContentView(R.layout.upload_success_layout)
                                successDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                                successDialog.findViewById<TextView>(R.id.dialogText).setText("Notification Sent Successfully to all the Class Mates!")
                                successDialog.findViewById<Button>(R.id.back_to_home).setOnClickListener {
                                    val intent = Intent(this@ReminderActivity,HomeActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                    startActivity(intent)
                                    successDialog.dismiss()
                                }
                                successDialog.setCanceledOnTouchOutside(false)
                                successDialog.setCancelable(false)
                                successDialog.show()


                               // Toast.makeText(baseContext, "Notification sent Successfully to all the Class Mates!", Toast.LENGTH_LONG).show()
                            }
                        }, object : Response.ErrorListener {
                            override fun onErrorResponse(error: VolleyError?) {
                                Toast.makeText(
                                    baseContext,
                                    "Error   " + error.toString(),
                                    Toast.LENGTH_LONG
                                ).show()

                            }
                        }) {
                        override fun getHeaders(): Map<String, String> {
                            val params: MutableMap<String, String> = HashMap()
                            params["Authorization"] = serverKey
                            params["Content-Type"] = contentType
                            return params
                        }
                    }
                    queue.add(request)
                } catch (e: Exception) {
                    e.printStackTrace()
                    btnName.isEnabled = true
                }

            }

        }
    }

    private fun setUpTypeList() {
        val typeNames = listOf("Assignment", "Lab Record", "Exam", "Others")
        val adapter = ArrayAdapter(
            this,
            R.layout.list_item, typeNames
        )
        (chooseType as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun isNullOrEmpty(str: String): Boolean {
        if (str != null && !str.trim().isEmpty())
            return false
        return true
    }


}