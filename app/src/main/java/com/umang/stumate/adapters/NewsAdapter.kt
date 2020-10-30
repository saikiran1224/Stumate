package com.umang.stumate.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.umang.stumate.R
import com.umang.stumate.modals.DashboardIconData
import com.umang.stumate.modals.NewsData

class NewsAdapter(private val newsList: ArrayList<NewsData>):
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.news_card, parent, false)
        return NewsAdapter.ViewHolder(v)

    }

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        holder.bindItems(newsList[position])
    }

    override fun getItemCount(): Int {
       return newsList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(news: NewsData) {

            val newsIcon = itemView.findViewById(R.id.news_icon) as ImageView
            val newsTitle = itemView.findViewById(R.id.news_title) as TextView
            val newsDescription = itemView.findViewById(R.id.news_description) as TextView

            newsTitle.text = news.newsTitle
            newsDescription.text = news.newsDescription
            newsIcon.setImageResource(news.newsPhoto)

        }
    }

}