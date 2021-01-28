package dev.kotlin.unwind.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.kotlin.unwind.R
import dev.kotlin.unwind.models.Content

class ContentViewActivityAdapter(
    private val context: Context,
    private val content: MutableList<Content?>
) : RecyclerView.Adapter<ContentViewActivityAdapter.ViewHolder>() {

    companion object {
        private const val COVER_IMAGE_WIDTH = 550
        private const val COVER_IMAGE_HEIGHT = 750
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.findViewById<ImageView>(R.id.ivContentMainInfoCover)
        private val textView = itemView.findViewById<TextView>(R.id.tvContentMainInfoTitle)
        private val imbdRatingView = itemView.findViewById<TextView>(R.id.tv_imbdrating)
        private val plotTextView = itemView.findViewById<TextView>(R.id.tv_plot)
        private val genreTextView = itemView.findViewById<TextView>(R.id.tv_genres)
        private val runtimeTextView = itemView.findViewById<TextView>(R.id.tv_runtime)


        fun bind(position: Int) {
            val item: Content? = content[position]
            val coverUrl = item?.getCoverImageUrl()
            imbdRatingView.text = item?.getIMBDRating()
            plotTextView.text = item?.getPlot()
            genreTextView.text = item?.getGenre()
            runtimeTextView.text = item?.getRuntime()




            if (coverUrl != "no_image") {
                Picasso.get().load(coverUrl).resize(
                    COVER_IMAGE_WIDTH,
                    COVER_IMAGE_HEIGHT
                ).into(imageView);
            } else {
                imageView.setImageResource(R.drawable.test)
                imageView.layoutParams.width =
                    COVER_IMAGE_WIDTH
                imageView.layoutParams.height =
                    COVER_IMAGE_HEIGHT
            }
            textView.text = item?.getTitle()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.content_view_details_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return content.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

}
