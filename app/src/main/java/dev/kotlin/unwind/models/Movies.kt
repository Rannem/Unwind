package dev.kotlin.unwind.models

class Movies (
    private val contentId: String,
    private val title: String,
    private val poster: String,
    private val IMBDRating: String,
    private val runtime: String,
    private val genre: String,
    private val plot: String

) : Content {

    override val contentType = ContentType.MOVIE

    override fun getTitle(): String {
        return title
    }

    override fun getContentId(): String {
        return contentId
    }

    override fun getCoverImageUrl(): String {
        if(poster == "N/A"){
            return "no_image"
        }
        return poster
    }

    override fun getIMBDRating(): String {
        return IMBDRating
    }

    override fun getPlot(): String {
        return plot
    }

    override fun getRuntime(): String {
        return runtime
    }

    override fun getGenre(): String {
        val split = genre.split(",")
        if (split.size > 2){
            return "" + split[0] + ", "+ split[1]+""
        } else{
            return genre
        }
    }

}
