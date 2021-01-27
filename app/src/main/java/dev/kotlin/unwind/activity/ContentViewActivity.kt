package dev.kotlin.unwind.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.kotlin.unwind.R
import dev.kotlin.unwind.adapters.ContentViewActivityAdapter
import dev.kotlin.unwind.models.Content
import dev.kotlin.unwind.models.ContentType
import dev.kotlin.unwind.utils.ApiHandler

class ContentViewActivity : AppCompatActivity() {

    companion object {
        private const val COVER_IMAGE_WIDTH = 550
        private const val COVER_IMAGE_HEIGHT = 750
    }

    private lateinit var adapter: ContentViewActivityAdapter

    private lateinit var rvContentViewDetailsDetails: RecyclerView

    private var content: MutableList<Content?> = mutableListOf()

    private val apiHandler = ApiHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_view)

        rvContentViewDetailsDetails = findViewById(R.id.rvContentViewDetailsDetails)

        content.add(intent.getSerializableExtra("content") as Content?)
        val contentId = intent.getSerializableExtra("contentId") as String
        val contentType = intent.getSerializableExtra("contentType") as ContentType

        adapter = ContentViewActivityAdapter(this, content)

        rvContentViewDetailsDetails.adapter = adapter
        rvContentViewDetailsDetails.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        if (false) {
            // Check if content exists in database and retrieve the information from there
        }

        setContent(contentType, contentId)




    }

    private fun setContent(contentType: ContentType, contentId: String): Content? {
        when (contentType){
            ContentType.MOVIE -> return apiHandler.setMovieById(this, contentId, adapter, content)
            ContentType.TV_SHOW -> return apiHandler.setTvShowById(this, contentId, adapter, content)
        }
        return null
    }

}