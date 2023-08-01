package ai.bale.theguardian.adapter

import ai.bale.theguardian.databinding.ListItemBinding
import ai.bale.theguardian.model.News
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load

class ItemAdapter(private val context: Context, private val dataset: List<News>): RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = ListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.itemTitle.text = item.title
        holder.itemCaption.text = item.body
        item.thumbnail.let{
            holder.itemThumbnail.load(item.thumbnail.toUri().buildUpon().scheme("https").build())
        }
    }
}