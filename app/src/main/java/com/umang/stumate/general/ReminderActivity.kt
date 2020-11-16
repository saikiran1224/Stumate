package com.umang.stumate.general

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.umang.stumate.R
import com.umang.stumate.auth.AuthenticationActivity
import com.umang.stumate.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_reminder.*
import org.json.JSONObject


class ReminderActivity : AppCompatActivity() {

    private val TAG = "TOKENS_DATA"
    private val FCM_API = "https://fcm.googleapis.com/fcm/send"
    private val serverKey = "key=" + AppPreferences.AUTH_KEY_FCM
    private val contentType = "application/json"
    var TOPIC: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        setUpTypeList()
        AppPreferences.init(this)

        remainderFAB.setOnClickListener {
            startActivity(Intent(this, UploadFilesActivity::class.java))
        }

        remainderBottomNav.setOnClickListener {
            val dialog= BottomSheetDialog(this,R.style.BottomSheetDialog)
            val view=layoutInflater.inflate(R.layout.bottom_items, null)

            view.findViewById<TextView>(R.id.classNotes).setOnClickListener {
                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.classNotes).setTextColor(resources.getColor(R.color.colorPrimary))

                startActivity(Intent(this, ClassNotesActivity::class.java))

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

                startActivity(Intent(this, ReminderActivity::class.java))

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

                startActivity(Intent(this, StudentProfileActivity::class.java))

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

                // Logout the user from session
                AppPreferences.isLogin = false
                AppPreferences.studentID = ""
                AppPreferences.studentName = ""
                startActivity(Intent(this, AuthenticationActivity::class.java))

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
                startActivity(Intent(this, ClassMatesActivity::class.java))

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

                edtDescription.error = null

                try {
                    val queue = Volley.newRequestQueue(this)
                    val url = "https://fcm.googleapis.com/fcm/send"


                    TOPIC = "/topics/"+AppPreferences.studentID

                    val data = JSONObject()
                    data.put("title", title + " regarding " + type)
                    data.put("message", description + "\n Sent by " + AppPreferences.studentName)
                    Log.e(TAG, "" + data)

                    val notification_data = JSONObject()
                    notification_data.put("data", data)
                    notification_data.put("to", TOPIC)

                    Log.e(TAG, "" + notification_data)
                    val request: JsonObjectRequest = object :
                        JsonObjectRequest(url, notification_data, object :
                            Response.Listener<JSONObject?> {
                            override fun onResponse(response: JSONObject?) {

                                Toast.makeText(baseContext,"Notification sent Successfully to all the Class Mates!",Toast.LENGTH_LONG).show()
                            }
                        }, object : Response.ErrorListener {
                            override fun onErrorResponse(error: VolleyError?) {
                                Toast.makeText(baseContext,"Error   "+error.toString(),Toast.LENGTH_LONG).show()

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
                }

            }

        }
    }

    private fun setUpTypeList() {
        val typeNames = listOf("Assignment","Lab Record","Exam","Others")
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