package dev.kotlin.unwind.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.squareup.picasso.Picasso
import dev.kotlin.unwind.R
import dev.kotlin.unwind.models.Content
import dev.kotlin.unwind.utils.ApiHandler

class ContentViewActivity : AppCompatActivity() {

    companion object {
        private const val COVER_IMAGE_WIDTH = 550
        private const val COVER_IMAGE_HEIGHT = 750
    }

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

        content = intent.getSerializableExtra("content") as Content?

        if (false) {
            // Check if content exists in database and retrieve the information from there
        }
        val coverUrl = content?.getCoverImageUrl()

        if (coverUrl != "no_image") {
            Picasso.get().load(coverUrl).resize(
                COVER_IMAGE_WIDTH,
                COVER_IMAGE_HEIGHT
            ).into(ivContentMainInfoCover);
        } else {
            ivContentMainInfoCover.setImageResource(R.drawable.test)
            ivContentMainInfoCover.layoutParams.width = COVER_IMAGE_WIDTH
            ivContentMainInfoCover.layoutParams.height = COVER_IMAGE_HEIGHT
        }

        val contentTitle = content?.getTitle()
        if (contentTitle != null) {
            tvContentMainInfoTitle.text = contentTitle
        }


    }

}