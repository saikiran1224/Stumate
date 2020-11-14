package com.umang.stumate.general

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import com.umang.stumate.R
import com.umang.stumate.modals.StudentData
import com.umang.stumate.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_student_profile.*
import kotlinx.android.synthetic.main.progress_bar.*

class StudentProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_profile)

        AppPreferences.init(this)

        retrieveStudentDetails(AppPreferences.studentEmailID.toString())

        closeButton.setOnClickListener { startActivity(Intent(this,HomeActivity::class.java)) }

        profileImage.visibility = View.GONE
        studentName.visibility = View.GONE
        phoneNumber.visibility = View.GONE
        collegeName.visibility = View.GONE
        departmentSection.visibility = View.GONE
        graduationYear.visibility = View.GONE

        loadingAnimationView.visibility = View.VISIBLE

    }

    private fun retrieveStudentDetails(emailID: String) {

        val myRef = FirebaseDatabase.getInstance().reference
        val studentDataQuery = myRef.child("students_data").orderByChild("emailID").equalTo(emailID)
        val studentDataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (ds in dataSnapshot.children) {
                        val studentData = ds.getValue(StudentData::class.java)
                        if (studentData != null) {

                            profileImage.visibility = View.VISIBLE
                            studentName.setText("|   " + studentData.studentName.toString())
                            studentName.visibility = View.VISIBLE
                            phoneNumber.setText("|   +91 " + studentData.studentPhoneNumber.toString())
                            phoneNumber.visibility = View.VISIBLE
                            collegeName.setText("|   " + studentData.collegeName.toString())
                            collegeName.visibility = View.VISIBLE
                            departmentSection.setText("|   "+ studentData.studentDept.toString() + " " + studentData.studentSection.toString())
                            departmentSection.visibility = View.VISIBLE
                            graduationYear.setText("|   Studying "+studentData.graduationYear.toString())
                            graduationYear.visibility = View.VISIBLE

                            loadingAnimationView.visibility = View.GONE

                        }

                    }
            } else {

                    Toast.makeText(baseContext,"No Data Found!",Toast.LENGTH_LONG).show()
                }

            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    baseContext,
                    error.message,
                    Toast.LENGTH_LONG
                ).show()



            }
        }
        studentDataQuery.addListenerForSingleValueEvent(studentDataListener)
    }

}