package ai.bale.theguardian.adapter


import ai.bale.theguardian.R
import ai.bale.theguardian.data.SettingsData
import android.widget.TextView
import ai.bale.theguardian.databinding.ListItemBinding
import ai.bale.theguardian.model.News
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.TooltipCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.core.view.allViews
import androidx.preference.PreferenceManager
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
            applyChanges()
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
            binding.shareButton.setOnClickListener {
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, new.url)
                }
                binding.root.context.startActivity(Intent.createChooser
                    (shareIntent, "Share using"))
            }

            TooltipCompat.setTooltipText(binding.shareButton, "Share")

            binding.root.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(new.url))
                binding.root.context.startActivity(intent)
            }
        } catch (_: Exception) { }
    }

    @SuppressLint("ResourceType")
    private fun applyChanges(){
        val context = binding.root.context

        val color = when (SettingsData.colorTheme) {
            "#FFFFFF" -> R.color.white
            "#87CEEB" -> R.color.sky_blue
            "#00008B" -> R.color.dark_blue
            "#9400D3" -> R.color.violet
            "#90EE90" -> R.color.light_green
            "#008000" -> R.color.green
            else -> R.color.white
        }

        binding.cardViewHeader.setBackgroundColor(ContextCompat.getColor(context, color))

        val config = context.resources.configuration

        config.fontScale = SettingsData.textScale.toFloat()

        if (context != null) {
            val newContext = context.createConfigurationContext(config)
            newContext.resources.updateConfiguration(config, newContext.resources.displayMetrics)
        }
    }
}