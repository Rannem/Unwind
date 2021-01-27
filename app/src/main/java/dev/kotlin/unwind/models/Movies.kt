package dev.kotlin.unwind.models

class Movies (
    private val contentId: String,
    private val title: String,
    private val poster: String,
    private val metaScore: String
) : Content {

    override val contentType = ContentType.MOVIE

    override fun getTitle(): String {
        return title
    }

    override fun getContentId(): String {
        return contentId
    }

    override fun getCoverImageUrl(): String {
        return poster
    }
}
