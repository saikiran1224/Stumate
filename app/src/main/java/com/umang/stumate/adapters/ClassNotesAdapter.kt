package com.umang.stumate.adapters

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.umang.stumate.R
import com.umang.stumate.modals.FileUploadData
import com.umang.stumate.utils.AppPreferences
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ClassNotesAdapter(val context: Context, private var classNotesList: ArrayList<FileUploadData>, private var studentNamePrefs: String?, private var studentID: String?):
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
        holder.bindItems(classNotesList[position], studentNamePrefs)

        holder.downloadButton.setOnClickListener {
           //downloadAndOpenPDF(holder.fileURL.text.toString())
           /* val webView = WebView(context)
            webView.loadUrl(holder.fileURL.text.toString())
            webView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
                //val i = Intent(Intent.ACTION_VIEW)
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(holder.fileURL.text.toString())))

                //i.data = Uri.parse(holder.fileURL.text.toString())
                //context.startActivity(i)
            }*/



            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(holder.fileURL.text.toString())
                )
            )

            //Toast.makeText(context, "Please see Notifications for Downloaded File after clicked on the Browser", Toast.LENGTH_LONG).show()
        }

        holder.viewPdfButton.setOnClickListener {

            // creating an Reference of the Object in Storage
            val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(holder.fileURL.text.toString())

            storageReference.getMetadata()
                .addOnSuccessListener(OnSuccessListener<StorageMetadata> { storageMetadata -> //here, storageMetadata contains all details about your file stored on FirebaseStorage
                    Log.i("tag", "name of file: " + storageMetadata.name)
                    Log.i("tag", "size of file in bytes: " + storageMetadata.sizeBytes)
                    Log.i("tag", "content type of file: " + storageMetadata.contentType)

                    // Creating a chooser for User
                    val intent = Intent(Intent.ACTION_VIEW)

                    // Checking the file type to filter the options in Chooser
                    if (storageMetadata.contentType?.startsWith("image") == true) {
                        intent.setDataAndType(Uri.parse(holder.fileURL.text.toString()), "image/*")
                    } else if(storageMetadata.contentType?.startsWith("application") == true) {
                        intent.setDataAndType(Uri.parse(holder.fileURL.text.toString()), "application/*")
                    } else {
                        Toast.makeText(context, "Sorry Your Phone has not Supported application to open this File", Toast.LENGTH_LONG).show()
                    }


                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

                    // Creating an Intent
                    val newIntent = Intent.createChooser(intent, "Open File")
                    try {
                        context.startActivity(newIntent)
                    } catch (e: ActivityNotFoundException) {
                        // Instruct the user to install a PDF reader here, or something
                        Toast.makeText(
                            context,
                            "Your phone has not Supported Applications",
                            Toast.LENGTH_LONG
                        ).show()
                    }


                })
                .addOnFailureListener(OnFailureListener { exception -> // Uh-oh, an error occurred!
                    Toast.makeText(context,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
                })


        }

        holder.fileShareButton.setOnClickListener {

            val fileShareText = AppPreferences.studentName + " has shared *" + holder.txtFileName.text.toString() + "* of " + holder.txtUnitName.text.toString() + " with you.\n\nPlease click on the following link to Download the file\n"+ holder.fileURL.text.toString()
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, fileShareText)
            sendIntent.type = "text/plain"
            context.startActivity(sendIntent)

        }

        holder.txtDeleteIcon.setOnClickListener{

            val deleteDialog = Dialog(context)
            deleteDialog.setContentView(R.layout.delete_file_dialog)
            deleteDialog.setCancelable(false)
            deleteDialog.setCanceledOnTouchOutside(false)
            deleteDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)

            deleteDialog.findViewById<Button>(R.id.btnDelete).setOnClickListener {

                val myRef = FirebaseDatabase.getInstance().getReference(studentID.toString()).child("files_data")
                val query = myRef.orderByChild("fileURL").equalTo(holder.fileURL.text.toString())
                val deleteEventListener = object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {

                        for (ds in snapshot.children) {
                            //removes data from Firebase
                            ds.ref.removeValue()
                        }

                        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(
                            holder.fileURL.text.toString()
                        )
                        storageReference.delete().addOnSuccessListener {
                            Toast.makeText(
                                context,
                                "File Deleted Successfully !",
                                Toast.LENGTH_LONG
                            ).show()
                        }.addOnFailureListener{
                            Toast.makeText(
                                context,
                                "Some Error Occurred. Please try again!!!",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        classNotesList.removeAt(holder.adapterPosition)
                        notifyItemRemoved(holder.adapterPosition)
                        notifyItemRangeChanged(holder.adapterPosition, classNotesList.size)
                        holder.itemView.visibility = View.GONE
                        deleteDialog.dismiss()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(context, " " + error.message.toString(), Toast.LENGTH_LONG).show()
                    }
                }
                query.addListenerForSingleValueEvent(deleteEventListener)

              /*  if(holder.txtStudentName.equals(studentNamePrefs)) {
                   holder.txtDeleteIcon.visibility = View.VISIBLE
                    Toast.makeText(context, "Authorized to delete", Toast.LENGTH_LONG).show()

                } else {
                    holder.txtDeleteIcon.visibility = View.GONE
                    Toast.makeText(context, "Not authorized to delete", Toast.LENGTH_LONG).show()

                }*/
                //  holder.bindItems(classNotesList[position], studentNamePrefs)

                //Toast.makeText(context,""+classNotesList.size.toString(),Toast.LENGTH_LONG).show()
            }

            deleteDialog.findViewById<Button>(R.id.btnCancel).setOnClickListener {
                deleteDialog.dismiss()
            }

            deleteDialog.show()
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
        val txtDeleteIcon  = itemView.findViewById(R.id.deleteIcon) as TextView
        val downloadButton = itemView.findViewById(R.id.btnDownload) as TextView
        val fileShareButton = itemView.findViewById(R.id.btnShare) as TextView
        val viewPdfButton = itemView.findViewById(R.id.btnView) as TextView

        // Invisible Text View holding file Download URL
        val fileURL = itemView.findViewById(R.id.txtInvisibleURL) as TextView


        fun bindItems(classNotes: FileUploadData, studentNamePrefs: String?) {

            @SuppressLint("SimpleDateFormat") val inputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")

            if(classNotes.studentName.toString().equals(studentNamePrefs.toString())) {
                txtDeleteIcon.visibility= View.VISIBLE
            } else {
                txtDeleteIcon.visibility = View.GONE
            }


            try {
                val date: Date = inputFormat.parse(classNotes.dateOfPublishing.toString())!!
                val niceDateStr = DateUtils.getRelativeTimeSpanString(
                    date.getTime(),
                    Calendar.getInstance().getTimeInMillis(),
                    DateUtils.MINUTE_IN_MILLIS
                ).toString()
                txtStudentName.text = "Posted by " + classNotes.studentName.toString() + " | " + niceDateStr.toString()

            } catch (e: ParseException) {
                e.printStackTrace()
            }


            txtFileName.text = classNotes.fileName
            txtSubjectName.text = classNotes.subjectName
            if(classNotes.unitNumber.toString().equals("Other Files")) {
                txtUnitName.text = "Other Files"
            } else {
                txtUnitName.text = "Unit-" + classNotes.unitNumber.toString()
            }
            fileURL.text = classNotes.fileURL
        }
    }

    fun filterList(filteredList: List<FileUploadData?>) {
        classNotesList = filteredList as ArrayList<FileUploadData>
        notifyDataSetChanged()
    }



}

private fun AppPreferences.init(context: ClassNotesAdapter.ViewHolder) {


}
