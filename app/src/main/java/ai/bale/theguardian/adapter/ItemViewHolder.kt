package ai.bale.theguardian.adapter

import android.view.View
import android.widget.TextView
import ai.bale.theguardian.R
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ItemViewHolder (private val view : View) : RecyclerView.ViewHolder(view) {
    val item_title: TextView = view.findViewById(R.id.news_title)
    val item_caption: TextView = view.findViewById(R.id.news_caption)
    val item_thumbnail: ImageView = view.findViewById(R.id.news_thumbnail)
}