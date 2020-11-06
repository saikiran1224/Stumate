package com.umang.stumate.general

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.chip.ChipGroup
import com.umang.stumate.R
import kotlinx.android.synthetic.main.activity_filter_subjects.*

class FilterSubjectsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_subjects)

        btnApplyChanges.setOnClickListener {
            val subjectChipGroup = subjectsChipGroup.checkedChipIds
            

            val subjectsList = subjectsChipGroup.checkedChipIds
            Toast.makeText(baseContext," " + subjectChipGroup, Toast.LENGTH_LONG).show()
        }


    }




}