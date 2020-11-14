package com.umang.stumate.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.umang.stumate.R
import com.umang.stumate.modals.FileUploadData
import com.umang.stumate.utils.AppPreferences
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ClassNotesAdapter(val context: Context, private var classNotesList: ArrayList<FileUploadData>):
    RecyclerView.Adapter<ClassNotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassNotesAdapter.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.classnotes_display_card,
            parent,
            false
        )
        return ClassNotesAdapter.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(classNotesList[position])

        holder.downloadButton.setOnClickListener {
           //downloadAndOpenPDF(holder.fileURL.text.toString())
            val webView = WebView(context)
            webView.loadUrl(holder.fileURL.text.toString())
            webView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(holder.fileURL.text.toString())
                context.startActivity(i)
            }

            Toast.makeText(context, "Please see Notifications for Downloaded File after clicked on the Browser", Toast.LENGTH_LONG).show()
        }

        holder.fileShareButton.setOnClickListener {

            val fileShareText = AppPreferences.studentName + " has shared *" + holder.txtFileName.text.toString() + "* of " + holder.txtUnitName.text.toString() + " with you.\n\nPlease click on the following link to Download the file\n"+ holder.fileURL.text.toString()
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, fileShareText)
            sendIntent.type = "text/plain"
            context.startActivity(sendIntent)

        }



    }

    override fun getItemCount(): Int {
        return classNotesList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtFileName = itemView.findViewById(R.id.fileTitle) as TextView
        val txtSubjectName  = itemView.findViewById(R.id.subjectName) as TextView
        val txtUnitName  = itemView.findViewById(R.id.unitNumber) as TextView
        val txtStudentName  = itemView.findViewById(R.id.studentName) as TextView
        val txtDateOfPublishing  = itemView.findViewById(R.id.dateOfPublishing) as TextView
        val downloadButton = itemView.findViewById(R.id.btnDownload) as TextView
        val fileShareButton = itemView.findViewById(R.id.btnShare) as TextView

        // Invisible Text View holding file Download URL
        val fileURL = itemView.findViewById(R.id.txtInvisibleURL) as TextView


        fun bindItems(classNotes: FileUploadData) {

            @SuppressLint("SimpleDateFormat") val inputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")

            try {
                val date: Date = inputFormat.parse(classNotes.dateOfPublishing.toString())!!
                val niceDateStr = DateUtils.getRelativeTimeSpanString(date.getTime(), Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS).toString()
                txtDateOfPublishing.text = niceDateStr.toString()
            } catch (e: ParseException) {
                e.printStackTrace()
            }


            txtFileName.text = classNotes.fileName
            txtSubjectName.text = classNotes.subjectName
            txtUnitName.text = "Unit-" + classNotes.unitNumber.toString()
            txtStudentName.text = "Posted by " + classNotes.studentName.toString()

            fileURL.text = classNotes.fileURL
        }
    }

    fun filterList(filteredList: List<FileUploadData?>) {
        classNotesList = filteredList as ArrayList<FileUploadData>
        notifyDataSetChanged()
    }



}
