package com.umang.stumate.general

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.umang.stumate.R
import com.umang.stumate.modals.FileUploadData
import com.umang.stumate.modals.NotificationData
import com.umang.stumate.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_upload_files.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class UploadFilesActivity : AppCompatActivity() {

    private lateinit var closeButton: ImageView

    val REQUEST_CODE_DOC : Int = 0
    private var fileDownloadUrl: String? = ""
    private var fileSelected: String? = "0"

    private  var unitNumber: String? = ""
    private  var fileType: String? = ""

    private val TAG = "TOKENS_DATA"
    private val FCM_API = "https://fcm.googleapis.com/fcm/send"
    private val serverKey = "key=" + AppPreferences.AUTH_KEY_FCM
    private val contentType = "application/json"
    var TOPIC: String? = null


    lateinit var uri : Uri
    lateinit var mStorage : StorageReference

    private lateinit var database: DatabaseReference

    @SuppressLint("ShowToast", "ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_files)

        AppPreferences.init(this)
        //setUpSubjectList()

        database = FirebaseDatabase.getInstance().reference

        mStorage = FirebaseStorage.getInstance().getReference(AppPreferences.studentID.toString())

        uploadFileButton.setOnClickListener {

            val mimeTypes = arrayOf(
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",  // .doc & .docx
                "application/vnd.ms-powerpoint",
                "application/vnd.openxmlformats-officedocument.presentationml.presentation",  // .ppt & .pptx
                "text/plain",
                "application/pdf", // PDF
                "application/zip",
                "image/*"// Image
            )

            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)

            intent.type = if (mimeTypes.size == 1) mimeTypes[0] else "*/*"
            if (mimeTypes.size > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            }
            startActivityForResult(Intent.createChooser(intent, "ChooseFile"), REQUEST_CODE_DOC)

        }

        oneUnit.setOnClickListener {

            oneUnit.setBackgroundResource(R.drawable.blue_rounded_button)
            oneUnit.setTextColor(resources.getColor(R.color.colorWhite))

            unitNumber = "1"

            secondUnit.setBackgroundResource(R.drawable.rounded_button)
            thirdUnit.setBackgroundResource(R.drawable.rounded_button)
            fourthUnit.setBackgroundResource(R.drawable.rounded_button)
            other.setBackgroundResource(R.drawable.rounded_button)

            secondUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            thirdUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            fourthUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            other.setTextColor(resources.getColor(R.color.colorPrimary))

        }
        secondUnit.setOnClickListener {
            secondUnit.setBackgroundResource(R.drawable.blue_rounded_button)
            secondUnit.setTextColor(resources.getColor(R.color.colorWhite))

            unitNumber = "2"

            oneUnit.setBackgroundResource(R.drawable.rounded_button)
            thirdUnit.setBackgroundResource(R.drawable.rounded_button)
            fourthUnit.setBackgroundResource(R.drawable.rounded_button)
            other.setBackgroundResource(R.drawable.rounded_button)

            oneUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            thirdUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            fourthUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            other.setTextColor(resources.getColor(R.color.colorPrimary))

        }

        thirdUnit.setOnClickListener {
            thirdUnit.setBackgroundResource(R.drawable.blue_rounded_button)
            thirdUnit.setTextColor(resources.getColor(R.color.colorWhite))

            unitNumber = "3"

            oneUnit.setBackgroundResource(R.drawable.rounded_button)
            secondUnit.setBackgroundResource(R.drawable.rounded_button)
            fourthUnit.setBackgroundResource(R.drawable.rounded_button)
            other.setBackgroundResource(R.drawable.rounded_button)

            oneUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            secondUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            fourthUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            other.setTextColor(resources.getColor(R.color.colorPrimary))

        }

        fourthUnit.setOnClickListener {
            fourthUnit.setBackgroundResource(R.drawable.blue_rounded_button)
            fourthUnit.setTextColor(resources.getColor(R.color.colorWhite))

            unitNumber = "4"

            oneUnit.setBackgroundResource(R.drawable.rounded_button)
            thirdUnit.setBackgroundResource(R.drawable.rounded_button)
            secondUnit.setBackgroundResource(R.drawable.rounded_button)
            other.setBackgroundResource(R.drawable.rounded_button)

            oneUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            thirdUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            secondUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            other.setTextColor(resources.getColor(R.color.colorPrimary))

        }

        other.setOnClickListener {
            other.setBackgroundResource(R.drawable.blue_rounded_button)
            other.setTextColor(resources.getColor(R.color.colorWhite))

            unitNumber = "Other Files"

            oneUnit.setBackgroundResource(R.drawable.rounded_button)
            thirdUnit.setBackgroundResource(R.drawable.rounded_button)
            fourthUnit.setBackgroundResource(R.drawable.rounded_button)
            secondUnit.setBackgroundResource(R.drawable.rounded_button)

            oneUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            thirdUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            fourthUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            secondUnit.setTextColor(resources.getColor(R.color.colorPrimary))

        }

        imageFormat.setOnClickListener {
            imageFormat.setBackgroundResource(R.drawable.blue_rounded_button)
            imageFormat.setTextColor(resources.getColor(R.color.colorWhite))

            fileType = "Image"

            pdfFormat.setBackgroundResource(R.drawable.rounded_button)
            pdfFormat.setTextColor(resources.getColor(R.color.colorPrimary))
            pptFormat.setBackgroundResource(R.drawable.rounded_button)
            pptFormat.setTextColor(resources.getColor(R.color.colorPrimary))
            docxFormat.setBackgroundResource(R.drawable.rounded_button)
            docxFormat.setTextColor(resources.getColor(R.color.colorPrimary))


        }


        pdfFormat.setOnClickListener {
            pdfFormat.setBackgroundResource(R.drawable.blue_rounded_button)
            pdfFormat.setTextColor(resources.getColor(R.color.colorWhite))

            fileType = "PDF"

            pptFormat.setBackgroundResource(R.drawable.rounded_button)
            pptFormat.setTextColor(resources.getColor(R.color.colorPrimary))
            docxFormat.setBackgroundResource(R.drawable.rounded_button)
            docxFormat.setTextColor(resources.getColor(R.color.colorPrimary))
            imageFormat.setBackgroundResource(R.drawable.rounded_button)
            imageFormat.setTextColor(resources.getColor(R.color.colorPrimary))



        }

        pptFormat.setOnClickListener {
            pptFormat.setBackgroundResource(R.drawable.blue_rounded_button)
            pptFormat.setTextColor(resources.getColor(R.color.colorWhite))

            fileType = "PPT"

            docxFormat.setBackgroundResource(R.drawable.rounded_button)
            docxFormat.setTextColor(resources.getColor(R.color.colorPrimary))
            pdfFormat.setBackgroundResource(R.drawable.rounded_button)
            pdfFormat.setTextColor(resources.getColor(R.color.colorPrimary))
            imageFormat.setBackgroundResource(R.drawable.rounded_button)
            imageFormat.setTextColor(resources.getColor(R.color.colorPrimary))


        }

        docxFormat.setOnClickListener {
            docxFormat.setBackgroundResource(R.drawable.blue_rounded_button)
            docxFormat.setTextColor(resources.getColor(R.color.colorWhite))

            fileType = "DOC"

            pptFormat.setBackgroundResource(R.drawable.rounded_button)
            pptFormat.setTextColor(resources.getColor(R.color.colorPrimary))
            pdfFormat.setBackgroundResource(R.drawable.rounded_button)
            pdfFormat.setTextColor(resources.getColor(R.color.colorPrimary))
            imageFormat.setBackgroundResource(R.drawable.rounded_button)
            imageFormat.setTextColor(resources.getColor(R.color.colorPrimary))


        }


        publishFile.setOnClickListener{

            val fileName: String = editFileTitle.text.toString()
            val subjectName: String = subjectNameSpinner.text.toString()

            if(fileSelected.equals("0")) {
                Toast.makeText(this, "Please select File", Toast.LENGTH_LONG).show()
            } else if(isNullOrEmpty(fileName)) {
                edtFileTitle.error = "Please enter Title for File"
            } else if(isNullOrEmpty(subjectName)) {
                edtFileTitle.error = null
                edtSubjectName.error = "Please select related Subject"
            } else if(unitNumber?.let { it1 -> isNullOrEmpty(it1) }!!) {
                edtSubjectName.error = null
                edtFileTitle.error = null

                Toast.makeText(this, "Please choose Unit Number", Toast.LENGTH_LONG).show()
            } else if(fileType?.let { it1 -> isNullOrEmpty(it1) }!!){
                edtSubjectName.error = null
                edtFileTitle.error = null
                Toast.makeText(this, "Please choose File Type", Toast.LENGTH_LONG).show()
            } else {
                edtSubjectName.error = null
                edtFileTitle.error = null

                edtFileTitle.isEnabled = false
                editFileTitle.isEnabled = false
                edtSubjectName.isEnabled = false
                deleteIcon.isEnabled = false

                oneUnit.isEnabled = false
                secondUnit.isEnabled = false
                thirdUnit.isEnabled = false
                fourthUnit.isEnabled = false
                other.isEnabled = false

                pptFormat.isEnabled = false
                pdfFormat.isEnabled = false

                upload(fileName, subjectName, unitNumber, fileType)

            }

        }

        closeButton = findViewById(R.id.closeButton)
        closeButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
            
        }

        deleteIcon.setOnClickListener {

            fileSelected = "0"

            uploadFileButton.text = "Upload File"
            statusIcon.visibility = VISIBLE
            deleteIcon.visibility = GONE
            uploadFileButton.isEnabled = true

            greenStatusIcon.visibility = GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            uri = data!!.data!!

            fileSelected = "1"
            statusIcon.visibility = GONE
            greenStatusIcon.visibility = VISIBLE
            uploadFileButton.text = "File Selected !"
            uploadFileButton.isEnabled = false

            deleteIcon.visibility = VISIBLE

            //upload()
        }
    }

    

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun upload(
        fileName: String?,
        subjectName: String?,
        unitNumber: String?,
        fileType: String?
    ) {

        var mReference = mStorage.child(fileName.toString())

        Toast.makeText(
            this,
            "Upload in Progress, Please don't close this Window",
            Toast.LENGTH_SHORT
        ).show()

        mReference.putFile(uri).addOnProgressListener { (bytesTransferred, totalByteCount) ->

            val progress = (100.0 * bytesTransferred) / totalByteCount

            publishFile.visibility = INVISIBLE

            customProgressBar.visibility = VISIBLE
            txtProgress.visibility = VISIBLE

            customProgressBar.progress =  progress.toInt()
            txtProgress.text = "${progress.toInt()}%"


        }.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }

            mReference.downloadUrl

        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {

               var downloadUri = task.result.toString()

                customProgressBar.visibility = GONE
                txtProgress.visibility = GONE

                publishFile.visibility = VISIBLE
                publishFile.text = "Successfully Uploaded !"
                publishFile.setBackgroundResource(R.drawable.green_rounded_button)

                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                val currentDate = sdf.format(Date())


                val fileData = FileUploadData(
                    fileName,
                    subjectName,
                    unitNumber,
                    fileType,
                    downloadUri.toString(),
                    AppPreferences.studentName,
                    currentDate.toString()
                )
                database.child(AppPreferences.studentID).child("files_data").push().setValue(
                    fileData
                )



                try {
                    val queue = Volley.newRequestQueue(this)
                    val url = "https://fcm.googleapis.com/fcm/send"


                    TOPIC = "/topics/"+AppPreferences.studentID

                    val data = JSONObject()
                    data.put("title",  AppPreferences.studentName + " Uploaded a File!" )
                    data.put("message", fileName + " of " + subjectName + " Uploaded into Stumate" + "\nPlease tap here to open the Class Notes Page")

                    Log.e(TAG, "" + data)

                    val notification_data = JSONObject()
                    notification_data.put("data", data)
                    notification_data.put("to", TOPIC)

                    Log.e(TAG, "" + notification_data)
                    val request: JsonObjectRequest = object :
                        JsonObjectRequest(url, notification_data, object :
                            Response.Listener<JSONObject?> {
                            override fun onResponse(response: JSONObject?) {

                                var dialog = Dialog(this@UploadFilesActivity)
                                dialog.setContentView(R.layout.upload_success_layout)
                                dialog.setCanceledOnTouchOutside(false)
                                dialog.setCancelable(false)
                                dialog.findViewById<MaterialButton>(R.id.back_to_home).setOnClickListener {
                                    dialog.dismiss()
                                    val intent = Intent(this@UploadFilesActivity, HomeActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                                    startActivity(intent)
                                    finish()

                                }
                                dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)

                                dialog.show()


                                // Toast.makeText(baseContext, "Notification sent Successfully to all the Class Mates!", Toast.LENGTH_LONG).show()
                            }
                        }, object : Response.ErrorListener {
                            override fun onErrorResponse(error: VolleyError?) {
                                Toast.makeText(
                                    baseContext,
                                    "Error:   " + error.toString(),
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
                }



            } else {
                // Handle failures
                // ...
                Toast.makeText(this, "Something Error Occurred !", Toast.LENGTH_LONG).show()

                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                finish()
                
            }
        }

    }


/*
    private fun setUpSubjectList() {
        val subjectNames = listOf("Probability and Statistics", "Web Technologies", "Computer Networks", "Operating Systems","Software Engineering","Computer Organization")
        val adapter = ArrayAdapter(this,
            R.layout.list_item, subjectNames)
        (subjectNameSpinner as? AutoCompleteTextView)?.setAdapter(adapter)
    }
*/

    private fun isNullOrEmpty(str: String): Boolean {
        if (str != null && !str.trim().isEmpty())
            return false
        return true
    }



}

private operator fun UploadTask.TaskSnapshot.component2(): Long {
    return totalByteCount.toLong()

}

private operator fun UploadTask.TaskSnapshot.component1(): Long {

    return bytesTransferred.toLong()
}


