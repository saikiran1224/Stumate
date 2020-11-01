package com.umang.stumate.adapters

import android.R.attr.path
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.umang.stumate.R
import com.umang.stumate.modals.FileUploadData
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


class ClassNotesAdapter(val context: Context, private val classNotesList: ArrayList<FileUploadData>):
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

            Toast.makeText(context, "Please see Notifications for Downloaded File", Toast.LENGTH_LONG).show()
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

        // Invisible Text View holding file Download URL
        val fileURL = itemView.findViewById(R.id.txtInvisibleURL) as TextView


        fun bindItems(classNotes: FileUploadData) {

            txtFileName.text = classNotes.fileName
            txtSubjectName.text = classNotes.subjectName
            txtUnitName.text = "Unit-" + classNotes.unitNumber
            txtStudentName.text = "Posted by " + classNotes.studentName
            txtDateOfPublishing.text = classNotes.dateOfPublishing

            fileURL.text = classNotes.fileURL
        }
    }


/*
    fun downloadAndOpenPDF(download_file_url: String?) {
        Thread {
            val path: Uri = Uri.fromFile(downloadFile(download_file_url))
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(path, "application/pdf")
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                context.startActivity(intent)

            } catch (e: ActivityNotFoundException) {
               // Toast.makeText(ContextCompat,"PDF Reader application is not installed in your device")
            }
        }.start()
    }
*/



/*
    fun downloadFile(dwnload_file_path: String?): File? {
        var file: File? = null
        val dest_file_path = "test.pdf"
        var downloadedSize = 0
        val totalsize: Int
        var per = 0f

        try {
            val url = URL(dwnload_file_path)
            val urlConnection: HttpURLConnection = url
                .openConnection() as HttpURLConnection
            urlConnection.setRequestMethod("GET")
            urlConnection.setDoOutput(true)

            // connect
            urlConnection.connect()

            // set the path where we want to save the file
            val SDCardRoot: File = Environment.getExternalStorageDirectory()
            // create a new file, to save the downloaded file
            file = File(SDCardRoot, dest_file_path)
            val fileOutput = FileOutputStream(file)

            // Stream used for reading the data from the internet
            val inputStream: InputStream = urlConnection.getInputStream()

            // this is the total size of the file which we are
            // downloading
            totalsize = urlConnection.getContentLength()


                //Toast.makeText(context, "Starting PDF download...", Toast.LENGTH_SHORT).show()


            // create a buffer...
            val buffer = ByteArray(1024 * 1024)
            var bufferLength = 0
            while (inputStream.read(buffer).also({ bufferLength = it }) > 0) {
                fileOutput.write(buffer, 0, bufferLength)
                downloadedSize += bufferLength
                per = downloadedSize as Float / totalsize * 100

               // Toast.makeText( context, "Total PDF File size  : " + totalsize / 1024 + " KB\n\nDownloading PDF " + per as Int + "% complete", Toast.LENGTH_SHORT ).show()
            }
            // close the output stream when complete //
            fileOutput.close()
           // Toast.makeText(context, "Download Complete. Open PDF Application installed in the device.", Toast.LENGTH_SHORT).show()
        } catch (e: MalformedURLException) {
            //Toast.makeText(context, "Some error occured. Press back and try again.", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
           // Toast.makeText(context, "Some error occured. Press back and try again.", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
           // Toast.makeText(context, "Failed to download image. Please check your internet connection.", Toast.LENGTH_SHORT).show()
        }

        return file
    }
*/



}
