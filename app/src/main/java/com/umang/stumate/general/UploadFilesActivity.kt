package com.umang.stumate.general

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.constraintlayout.solver.GoalRow
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.umang.stumate.R
import com.umang.stumate.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_student_details.*
import kotlinx.android.synthetic.main.activity_upload_files.*
import kotlin.math.roundToLong

class UploadFilesActivity : AppCompatActivity() {

    private lateinit var closeButton: ImageView

    val PDF : Int = 0

    lateinit var uri : Uri
    lateinit var mStorage : StorageReference

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_files)

        AppPreferences.init(this)
        setUpSubjectList()

        mStorage = FirebaseStorage.getInstance().getReference(AppPreferences.studentID.toString())

        uploadFileButton.setOnClickListener {
                view: View? -> val intent = Intent()
            intent.setType ("application/pdf")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select File to Upload"),PDF)
        }

        publishFile.setOnClickListener{
            Toast.makeText(this, " " + AppPreferences.studentID, Toast.LENGTH_SHORT).show()
        }

        closeButton = findViewById(R.id.closeButton)
        closeButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            uri = data!!.data!!
            upload()
        }
    }

    private fun upload() {

        var mReference = mStorage.child(uri.lastPathSegment!!)

        mReference.putFile(uri).addOnProgressListener {
                (bytesTransferred, totalByteCount) ->

            uploadFileButton.text = "Uploading..."
            uploadFileButton.isEnabled = false
            statusIcon.visibility = GONE

            val progress = (100.0 * bytesTransferred) / totalByteCount

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

        } .addOnCompleteListener { task ->
            if (task.isSuccessful) {

                val downloadUri = task.result

                customProgressBar.visibility = GONE
                txtProgress.visibility = GONE

                uploadFileButton.text = "Uploaded !"
                uploadFileButton.isEnabled = false

                greenStatusIcon.visibility = VISIBLE

            } else {
                // Handle failures
                // ...
                Toast.makeText(this,"Error !", Toast.LENGTH_LONG).show()

            }
        }



/*
        try {
            mReference.putFile(uri).addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot? ->
                if(taskSnapshot.)

                Toast.makeText(this, " "+url.toString(), Toast.LENGTH_LONG).show()
            }
        }catch (e: Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }
*/

    }




    private fun setUpSubjectList() {
        val subjectNames = listOf("Probability and Statistics", "Web Technologies", "Computer Networks", "Operating Systems","Software Engineering","Computer Organization")
        val adapter = ArrayAdapter(this,
            R.layout.list_item, subjectNames)
        (subjectNameSpinner as? AutoCompleteTextView)?.setAdapter(adapter)
    }

}

private operator fun UploadTask.TaskSnapshot.component2(): Long {
    return totalByteCount.toLong()

}

private operator fun UploadTask.TaskSnapshot.component1(): Long {

    return bytesTransferred.toLong()
}
