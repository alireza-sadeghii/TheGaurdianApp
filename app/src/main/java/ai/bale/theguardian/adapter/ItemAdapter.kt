package ai.bale.theguardian.adapter


import ai.bale.theguardian.databinding.ListItemBinding
import ai.bale.theguardian.model.News
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.jsoup.Jsoup
import java.lang.Exception

class ItemAdapter(private val context: Context?, private val dataset: List<News>) :
    RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = ListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]

        holder.itemThumbnail.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, item.url.toUri())
            context?.startActivity(browserIntent)
        }
        holder.itemTitle.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, item.url.toUri())
            context?.startActivity(browserIntent)
        }

        try {
            holder.itemTitle.text = item.fields.title
            holder.itemCaption.text = Jsoup.parse(item.fields.news_body).text().take(150)
            holder.itemAuthor.text = item.tags[0].getAuthor()
            holder.itemDate.text = item.pDate.split("T")[0]
            holder.itemTag.text = item.sectionName
            item.fields.image.let {
                holder.itemThumbnail.load(
                    item.fields.image.toUri().buildUpon().scheme("https").build()
                )
            }
        } catch (_: Exception) {

        }

    }
}