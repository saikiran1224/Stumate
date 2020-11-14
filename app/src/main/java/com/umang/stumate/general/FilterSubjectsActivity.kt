package com.umang.stumate.general

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.common.internal.StringResourceValueReader
import com.google.android.material.chip.ChipGroup
import com.umang.stumate.R
import kotlinx.android.synthetic.main.activity_filter_subjects.*
import kotlinx.android.synthetic.main.activity_upload_files.*

class FilterSubjectsActivity : AppCompatActivity() {

    private lateinit var checkedSubjectsList: ArrayList<String>
    private lateinit var checkedUnitsList: ArrayList<String>
    private lateinit var checkedFileTypeList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_subjects)

        checkedSubjectsList = ArrayList<String>()
        checkedUnitsList = ArrayList<String>()
        checkedFileTypeList = ArrayList<String>()

        //Subjects Check List
        PandS.setOnCheckedChangeListener { chip, isChecked -> checkedSubjectsList.add("Probability and Statistics") }
        ComputerNetworks.setOnCheckedChangeListener { _, _ -> checkedSubjectsList.add("Computer Networks") }
        ComputerOrganization.setOnCheckedChangeListener { chip, isChecked -> checkedSubjectsList.add("Computer Organization") }
        OperatingSystems.setOnCheckedChangeListener { chip, isChecked -> checkedSubjectsList.add("Operating Systems") }
        WebTechnologies.setOnCheckedChangeListener { chip, isChecked -> checkedSubjectsList.add("Web Technologies") }
        SoftwareEngineering.setOnCheckedChangeListener { chip, isChecked -> checkedSubjectsList.add("Software Engineering") }

        // Units Check List
        unitOne.setOnCheckedChangeListener { chip, isChecked -> checkedUnitsList.add("Unit-1") }
        unitTwo.setOnCheckedChangeListener { chip, isChecked -> checkedUnitsList.add("Unit-2") }
        unitThree.setOnCheckedChangeListener { chip, isChecked -> checkedUnitsList.add("Unit-3") }
        unitFour.setOnCheckedChangeListener { chip, isChecked -> checkedUnitsList.add("Unit-4") }
        QuestionPapers.setOnCheckedChangeListener { chip, isChecked -> checkedUnitsList.add("Question Papers") }
        Others.setOnCheckedChangeListener { chip, isChecked -> checkedUnitsList.add("Others") }

        //File Type Check List
        PDfFormat.setOnCheckedChangeListener { chip, isChecked -> checkedFileTypeList.add("PDF") }
        PPTformat.setOnCheckedChangeListener { chip, isChecked -> checkedFileTypeList.add("PPT") }
        otherFiles.setOnCheckedChangeListener { chip, isChecked -> checkedFileTypeList.add("Other Files") }

        btnApplyChanges.setOnClickListener {
            val intent = Intent(this,ClassNotesActivity::class.java)
            intent.putStringArrayListExtra("subjectsList",checkedSubjectsList)
            intent.putStringArrayListExtra("unitsList",checkedUnitsList)
            intent.putStringArrayListExtra("fileTypeList",checkedFileTypeList)
            startActivity(intent)
        }

    }




}