package dev.kotlin.unwind.models

class TvShow(
    private val contentId: Int,
    private val title: String
) : Content
{

    private var averageRating: Double = 0.0

    override val contentType = ContentType.TV_SHOW

    override fun getTitle(): String {
        return title
    }

    override fun getContentId(): Int {
        return contentId
    }

}