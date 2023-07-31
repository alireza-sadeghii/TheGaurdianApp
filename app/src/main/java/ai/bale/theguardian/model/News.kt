package ai.bale.theguardian.model

data class News(val id: String, val type: String, val sectionName: String, val sectionId: String,
                val pDate: String, val title: String, val url: String,
                val rating: Int, val thumbnail: String, val author: String,
                val body: String)