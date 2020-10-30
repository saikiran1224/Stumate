package com.umang.stumate.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.umang.stumate.R
import com.umang.stumate.modals.DashboardIconData

class DashboardIconsAdapter(private val iconsList: ArrayList<DashboardIconData>):
    RecyclerView.Adapter<DashboardIconsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardIconsAdapter.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.dashboard_card, parent, false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: DashboardIconsAdapter.ViewHolder, position: Int) {
        holder.bindItems(iconsList[position])
    }

    override fun getItemCount() = iconsList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(icon: DashboardIconData) {
            val textActionName = itemView.findViewById(R.id.actionName) as TextView
            val iconLogoDrawable  = itemView.findViewById(R.id.actionIcon) as ImageView

            textActionName.text = icon.iconActionName
            iconLogoDrawable.setImageResource(icon.iconDrawableName)

        }
    }

}