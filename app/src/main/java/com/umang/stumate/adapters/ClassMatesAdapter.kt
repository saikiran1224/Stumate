package com.umang.stumate.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.umang.stumate.R
import com.umang.stumate.modals.FileUploadData
import com.umang.stumate.modals.StudentData
import kotlinx.android.synthetic.main.activity_student_profile.*
import kotlinx.android.synthetic.main.classmate_view_card.view.*

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

        val profileText = itemView.profileTextLayout.findViewById(R.id.profileText) as TextView

        fun bindItems(classMates: StudentData) {

            txtStudentName.text = classMates.studentName
            txtMailId.text = classMates.emailID

            val studentName = classMates.studentName
            val count = classMates.studentName?.split(" ")!!.toTypedArray()

            if(count.size == 1) {
                profileText.text = studentName!![0].toString()
            } else {
                val index = studentName?.lastIndexOf(' ')
                val firstName = index?.let { it1 -> studentName?.substring(0, it1) }
                val lastName = index?.plus(1)?.let { it1 -> studentName?.substring(it1) }
                profileText.text = firstName?.toString()!![0] + lastName!![0]?.toString()
            }
        }

    }

}