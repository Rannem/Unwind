package dev.kotlin.unwind.models

import android.net.Uri

class TvShow(
    private val contentId: String,
    private val title: String,
    private val imageUrlSmall: String,
    private val imageUrlLarge: String,
    private val IMBDRating: String,
    private val runtime: String,
    private val genres: List<String>,
    private val plot: String
) : Content
{

    private var averageRating: Double = -1.0

    override val contentType = ContentType.TV_SHOW

    override fun getTitle(): String {
        return title
    }

    override fun getContentId(): String {
        return contentId
    }

    override fun getCoverImageUrl(): String {
        return imageUrlSmall
    }

    override fun getIMBDRating(): String {
        return IMBDRating.toString()
    }

    override fun getPlot(): String {
        return plot
    }

    override fun getRuntime(): String {
        return "$runtime min"
    }

    override fun getGenre(): String {
        if (genres.size > 2){
            return ""+ genres[0]+ ", "+ genres[1]+""
        }else
            return genres.toString()
    }

    fun getImageUrlSmall(): String {
        return imageUrlSmall
    }
    fun getImageUrlLarge(): String {
        return imageUrlLarge
    }

    fun getGenres(): List<String> {
        return genres
    }

    fun setAverageRating(newRating: Double) {
        averageRating = newRating
    }

    fun getAverageRating(): Double {
        return averageRating
    }


}