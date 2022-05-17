package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NewsItemAdapter(private val listener : NewsItemClicked): RecyclerView.Adapter<NewsItemViewHolder>() {
    private val items : ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        val viewHolder = NewsItemViewHolder(view)

        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateNews(updatedNews : ArrayList<News>) {
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }
}

class NewsItemViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
    val titleView : TextView = itemView.findViewById(R.id.newsItemTitle)
}

interface NewsItemClicked {
    fun onItemClicked(item : News)
}