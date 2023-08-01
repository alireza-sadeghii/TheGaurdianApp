package ai.bale.theguardian.adapter

import android.widget.TextView
import ai.bale.theguardian.databinding.ListItemBinding
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ItemViewHolder (private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    val itemTitle: TextView = binding.newsTitle
    val itemCaption: TextView = binding.newsCaption
    val itemThumbnail: ImageView = binding.newsThumbnail
}