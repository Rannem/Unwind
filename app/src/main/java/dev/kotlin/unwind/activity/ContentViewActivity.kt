package dev.kotlin.unwind.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import dev.kotlin.unwind.R
import dev.kotlin.unwind.models.Content
import dev.kotlin.unwind.models.ContentType
import dev.kotlin.unwind.models.Movies
import dev.kotlin.unwind.models.TvShow
import dev.kotlin.unwind.utils.ApiHandler

class ContentViewActivity : AppCompatActivity() {

    private lateinit var cvContentMainInfo: CardView
    private lateinit var ivContentMainInfoCover: ImageView
    private lateinit var tvContentMainInfoTitle: TextView
    private lateinit var tvContentDetailsDetails: TextView

    private var content: Content? = null
    private val apiHandler = ApiHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_view)

        cvContentMainInfo = findViewById(R.id.cvContentMainInfo)
        ivContentMainInfoCover = findViewById(R.id.ivContentMainInfoCover)
        tvContentMainInfoTitle = findViewById(R.id.tvContentMainInfoTitle)
        tvContentDetailsDetails = findViewById(R.id.tvContentDetailsDetails)

        val contentId = intent.getSerializableExtra("contentId") as Int
        val contentType = intent.getSerializableExtra("contentType") as ContentType
        content = findSpecificContent(contentId, contentType)

    }

    private fun findSpecificContent(contentId: Int, contentType: ContentType): Content? {
        when (contentType) {
            ContentType.TV_SHOW -> return findSpecificTvShow(contentId)
            ContentType.MOVIE -> return findSpecificMovie(contentId)
    }
        return null
}

    private fun findSpecificTvShow(contentId: Int): TvShow? {
        return apiHandler.getTvShowById(this, contentId)
    }

    private fun findSpecificMovie(contentId: Int): Movies? {
        return apiHandler.getMovieById(this, contentId)
    }
}