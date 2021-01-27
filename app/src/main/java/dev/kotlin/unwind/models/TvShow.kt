package dev.kotlin.unwind.models

import android.net.Uri

class TvShow(
    private val contentId: String,
    private val title: String,
    private val imageUrlSmall: String,
    private val imageUrlLarge: String,
    private val genres: List<String>
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