package dev.kotlin.unwind.models

class TvShow(
    private val identity: String,
    private val contentId: Int,
    private val title: String
) : Content
{

    override val contentType = ContentType.TV_SHOW

    override fun getTitle() {
        TODO("Not yet implemented")
    }

    override fun getContentId() {
        TODO("Not yet implemented")
    }

}