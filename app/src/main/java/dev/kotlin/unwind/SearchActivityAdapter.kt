package dev.kotlin.unwind

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.kotlin.unwind.models.Content

class SearchActivityAdapter(
    private val context: Context,
    private val content: MutableList<Content?>
) : RecyclerView.Adapter<SearchActivityAdapter.ViewHolder>() {

    companion object {
        private const val MARGIN_SIZE = 10
        private const val COVER_IMAGE_WIDTH = 550
        private const val COVER_IMAGE_HEIGHT = 750
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.findViewById<ImageView>(R.id.ivContentCardCover)
        private val textView = itemView.findViewById<TextView>(R.id.tvContentCardTitle)

        fun bind(position: Int) {
            val item: Content? = content[position]
            val coverUrl = item?.getCoverImageUrl()
            if (coverUrl != "no_image") {
                Picasso.get().load(coverUrl).resize(COVER_IMAGE_WIDTH, COVER_IMAGE_HEIGHT).into(imageView);
            } else {
                imageView.setImageResource(R.drawable.test)
                imageView.layoutParams.width = COVER_IMAGE_WIDTH
                imageView.layoutParams.height = COVER_IMAGE_HEIGHT
            }
            textView.text = item?.getTitle()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.content_card, parent, false)
        val layoutParams = view.findViewById<CardView>(R.id.cvContentCard).layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.setMargins(MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return content.size
    }
}

interface CardClickListener {
    fun onCardClick(position: Int)
}