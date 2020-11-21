package com.umang.stumate.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.umang.stumate.R
import com.umang.stumate.modals.NotificationData
import com.umang.stumate.modals.StudentData

class MissedNotificationsAdapter(val context: Context, private val notificationsList: ArrayList<NotificationData>):
    RecyclerView.Adapter<MissedNotificationsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissedNotificationsAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.missed_notification_card, parent,false)
        return MissedNotificationsAdapter.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: MissedNotificationsAdapter.ViewHolder, position: Int) {
        holder.bindItems(notificationsList[position])
    }

    override fun getItemCount(): Int {
        return notificationsList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val notificationTitle = itemView.findViewById(R.id.notificationTitle) as TextView
        val notificationDesc = itemView.findViewById(R.id.notificationDescription) as TextView
        val studentName = itemView.findViewById(R.id.studentName) as TextView

        fun bindItems(notification: NotificationData) {

            notificationTitle.text = notification.notificationTitle
            notificationDesc.text = notification.notificationDesc
            studentName.text = notification.studentName

        }



    }


}