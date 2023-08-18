package ai.bale.theguardian.adapter


import android.widget.TextView
import ai.bale.theguardian.databinding.ListItemBinding
import ai.bale.theguardian.model.News
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.jsoup.Jsoup
import java.lang.Exception

class ItemViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(new: News?) {
        if (new != null) {
            showNew(new)
        }
    }

    private fun showNew(new: News) {
        try {
            binding.newsTitle.text = new.fields.title
            binding.newsCaption.text = Jsoup.parse(new.fields.newsBody).text().take(150)
            new.fields.image.let {
                binding.newsThumbnail.load(
                    new.fields.image.toUri().buildUpon().scheme("https").build()
                )
            }
            binding.author.text = new.tags[0].getAuthor()
            binding.date.text = new.pDate.replace("T", "\n")
            binding.newsTag.text = new.sectionName
        }catch (_: Exception){ }
    }
}