package com.umang.stumate.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.umang.stumate.R
import com.umang.stumate.modals.StudentData
import kotlinx.android.synthetic.main.activity_student_details.*

class StudentDetailsActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)
        setUpCollegeList()
        setUpDepartmentList()
        setUpSectionList()
        database = Firebase.database.reference


        btnSubmit.setOnClickListener {
            val studentName = editName.text
            val studentPhoneNumber = editPhone.text
            val collegeName = collegeList.text
            val graduationYear = editGraduationYear.text
            val studentDept = deptSpinner.text
            val studentSection = sectionSpinner.text


            //TODO: Phone Number (Only 10 Digits length!=10) and Name (only Characters allowed) Validation

            if(isNullOrEmpty(studentName)) {
                edtName.error = "Please enter Name"
            } else if(isNullOrEmpty(studentPhoneNumber)) {
                edtName.error = null
                edtPhone.error = "Please enter Phone Number"
            } else if(isNullOrEmpty(collegeName)) {
                edtPhone.error = null
                edtCollegeName.error = "Please choose College Name"
            } else if(isNullOrEmpty(graduationYear)) {
                edtCollegeName.error = null
                edtGraduationYear.error = "Please enter Graduation Year E.g 2022"
            } else if(isNullOrEmpty(studentDept)) {
                edtGraduationYear.error = null
                edtDepartment.error = "Please choose Department"
            } else if(isNullOrEmpty(studentSection)) {
                edtDepartment.error = null
                edtSection.error = "Please choose Section"
            } else {
                edtSection.error = null
                val intent=intent
                val email=intent.getStringExtra("Email")

                val userID = ""+collegeName+" "+"_"+graduationYear + "_"+ studentDept+"_"+studentSection

                //TODO: Should send Email ID from Authentication Activity and pass Parameter to Database
                writeNewUser(userID,studentName,email, studentPhoneNumber, collegeName,graduationYear,studentDept,studentSection)

            }
        }
    }

    private fun writeNewUser(userId: String, name: Editable?, email: String?,phone: Editable?, collegeName: Editable?, graduationYear: Editable?, studentDept: Editable?, studentSection: Editable? ) {
        val user = StudentData(userId,
            name.toString(),email.toString(),phone.toString(),collegeName.toString(), graduationYear.toString(),studentDept.toString(),studentSection.toString())
        database.child("students_data").child(userId).setValue(user)
        showToast("Details Submitted Successfully !")
    }


    private fun isNullOrEmpty(str: Editable?): Boolean {
        if (str != null && !str.trim().isEmpty())
            return false
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    private fun setUpCollegeList() {
        val collegeNames = listOf("GMR Institute of Technology", "Lendi Institute of Technology", "Gayatri Vidya Parishad College ", "Raghu Institute of Technology")
        val adapter = ArrayAdapter(this,
            R.layout.list_item, collegeNames)
        (collegeList as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun setUpDepartmentList() {
        val deptNames = listOf("Computer Science Engineering", "Information Technology", "Electrical and Communication Engineering", "Electrical and Electronics Engineering","Civil Engineering","Mechanical Engineering","Power Engineering","Chemical Engineering")
        val adapter = ArrayAdapter(this,
            R.layout.list_item, deptNames)
        (deptSpinner as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun setUpSectionList() {
        val sectionNames = listOf("A Section", "B Section", "C Section")
        val adapter = ArrayAdapter(this,
            R.layout.list_item, sectionNames)
        (sectionSpinner as? AutoCompleteTextView)?.setAdapter(adapter)
    }

}