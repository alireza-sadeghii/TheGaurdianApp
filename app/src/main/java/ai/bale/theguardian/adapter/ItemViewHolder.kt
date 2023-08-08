package ai.bale.theguardian.adapter


import android.widget.TextView
import ai.bale.theguardian.databinding.ListItemBinding
import ai.bale.theguardian.model.News
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.jsoup.Jsoup

class ItemViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val itemTitle: TextView = binding.newsTitle
    private val itemCaption: TextView = binding.newsCaption
    private val itemThumbnail: ImageView = binding.newsThumbnail
    private val itemAuthor: TextView = binding.author
    private val itemDate: TextView = binding.date
    private val itemTag: TextView = binding.newsTag

    private var new: News? = null


    init {
        binding.root.setOnClickListener {
            new?.url.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                binding.root.context.startActivity(intent)
            }
        }
    }


    fun bind(new: News?) {
        if (new != null) {
            showNew(new)
        }
    }


    private fun showNew(new: News) {
        this.new = new
        itemTitle.text = new.fields.title
        itemCaption.text = Jsoup.parse(new.fields.news_body).text().take(150)
        new.fields.image.let {
            itemThumbnail.load(
                new.fields.image.toUri().buildUpon().scheme("https").build()
            )
        }
        itemAuthor.text = new.tags[0].getAuthor()
        itemDate.text = new.pDate.replace("T", "\n")
        itemTag.text = new.sectionName
    }

    companion object {
        fun create(parent: ViewGroup): ItemViewHolder {
            val adapterLayout = ListItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return ItemViewHolder(adapterLayout)
        }
    }
}