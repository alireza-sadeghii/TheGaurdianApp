package ai.bale.theguardian.adapter

import ai.bale.theguardian.R
import ai.bale.theguardian.model.News
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load

class ItemAdapter(private val context: Context, private val dataset: List<News>): RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.item_title.text = item.title
        holder.item_caption.text = item.body
        item.thumbnail?.let{
            holder.item_thumbnail.load(item.thumbnail.toUri().buildUpon().scheme("https").build())
        }
    }
}