package com.umang.stumate.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.umang.stumate.R
import com.umang.stumate.modals.DashboardIconData
import com.umang.stumate.modals.FileUploadData

class ClassNotesAdapter(private val classNotesList: ArrayList<FileUploadData>):
    RecyclerView.Adapter<ClassNotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassNotesAdapter.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.classnotes_display_card, parent, false)
        return ClassNotesAdapter.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(classNotesList[position])

    }

    override fun getItemCount(): Int {
        return classNotesList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(classNotes: FileUploadData) {

            val txtFileName = itemView.findViewById(R.id.fileTitle) as TextView
            val txtSubjectName  = itemView.findViewById(R.id.subjectName) as TextView
            val txtUnitName  = itemView.findViewById(R.id.unitNumber) as TextView
            val txtStudentName  = itemView.findViewById(R.id.studentName) as TextView
            val txtDateOfPublishing  = itemView.findViewById(R.id.dateOfPublishing) as TextView

            // Invisible Text View holding file Download URL
            val fileURL = itemView.findViewById(R.id.txtInvisibleURL) as TextView

            txtFileName.text = classNotes.fileName
            txtSubjectName.text = classNotes.subjectName
            txtUnitName.text = "Unit-" + classNotes.unitNumber
            txtStudentName.text = "Posted by " + classNotes.studentName
            txtDateOfPublishing.text = classNotes.dateOfPublishing

            fileURL.text = classNotes.fileURL
        }
    }

}
