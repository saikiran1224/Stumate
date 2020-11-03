package com.umang.stumate.general

import android.R.attr.name
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.umang.stumate.R
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

        AppPreferences.init(this)



        btnName.setOnClickListener {

            //var message = editMessage.text.toString()

          //  Toast.makeText(baseContext, " " + message, Toast.LENGTH_SHORT).show()




            try {
                val queue = Volley.newRequestQueue(this)
                val url = "https://fcm.googleapis.com/fcm/send"

                val message = editMessage.text.toString()


                TOPIC = "/topics/"+AppPreferences.studentID

                val data = JSONObject()
                data.put("title", "Reminder for " + AppPreferences.studentName)
                data.put("message", message)
                Log.e(TAG, "" + data)

                val notification_data = JSONObject()
                notification_data.put("data", data)
                notification_data.put("to", TOPIC)

                Log.e(TAG, "" + notification_data)
                val request: JsonObjectRequest = object :
                    JsonObjectRequest(url, notification_data, object :
                        Response.Listener<JSONObject?> {
                        override fun onResponse(response: JSONObject?) {

                            Toast.makeText(baseContext,response.toString(),Toast.LENGTH_LONG).show()
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