package com.umang.stumate.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.umang.stumate.R
import com.umang.stumate.modals.FileUploadData
import com.umang.stumate.modals.StudentData

class ClassMatesAdapter(val context: Context, private val classMatesList: ArrayList<StudentData>):
    RecyclerView.Adapter<ClassMatesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassMatesAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.classmate_view_card, parent, false)
        return ClassMatesAdapter.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ClassMatesAdapter.ViewHolder, position: Int) {
        holder.bindItems(classMatesList[position])
    }

    override fun getItemCount(): Int {
        return classMatesList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtStudentName = itemView.findViewById(R.id.studentName) as TextView
        val txtMailId  = itemView.findViewById(R.id.mailID) as TextView


        fun bindItems(classMates: StudentData) {

            txtStudentName.text = classMates.studentName
            txtMailId.text = classMates.emailID

        }
    }

}