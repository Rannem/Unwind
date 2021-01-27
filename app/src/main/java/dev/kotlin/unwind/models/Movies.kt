package dev.kotlin.unwind.models

class Movies (
    private val title: String,
    private val poster: String,
    private val metaScore: String
) : Content {

    override val contentType = ContentType.MOVIE

    override fun getTitle(): String {
        return title
    }

    override fun getContentId(): Int {
        return 0
    }

    override fun getCoverImageUrl(): String {
        return poster
    }

}
