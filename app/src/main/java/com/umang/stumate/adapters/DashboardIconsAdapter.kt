package com.umang.stumate.adapters

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.umang.stumate.R
import com.umang.stumate.general.*
import com.umang.stumate.modals.DashboardIconData
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


class DashboardIconsAdapter(private val iconsList: ArrayList<DashboardIconData>):
    RecyclerView.Adapter<DashboardIconsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardIconsAdapter.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.dashboard_card, parent, false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindItems(iconsList[position])

        holder.dashboardCard.setOnClickListener{
            if(holder.textActionName.text.equals("Class Notes")) {
                val intent = Intent(holder.textActionName.context, ClassNotesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                holder.textActionName.context.startActivity(intent)
            }

            if(holder.textActionName.text.equals("Class Mates")) {
                val intent = Intent(holder.textActionName.context, ClassMatesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                holder.textActionName.context.startActivity(intent)
            }

            if(holder.textActionName.text.equals("Reminders")) {
                val intent = Intent(holder.textActionName.context, ReminderActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                holder.textActionName.context.startActivity(intent)
            }

            if(holder.textActionName.text.equals("Profile")) {
                val intent = Intent(holder.textActionName.context, StudentProfileActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                holder.textActionName.context.startActivity(intent)
            }

        }

    }

    override fun getItemCount() = iconsList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textActionName = itemView.findViewById(R.id.actionName) as TextView
        val iconLogoDrawable  = itemView.findViewById(R.id.actionIcon) as ImageView
        val dashboardCard = itemView.findViewById(R.id.dashboardCard) as CardView

        fun bindItems(icon: DashboardIconData) {

            textActionName.text = icon.iconActionName
            iconLogoDrawable.setImageResource(icon.iconDrawableName)

        }
    }
}