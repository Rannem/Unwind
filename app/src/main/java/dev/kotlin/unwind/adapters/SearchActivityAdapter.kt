package dev.kotlin.unwind.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.kotlin.unwind.R
import dev.kotlin.unwind.models.Content

class SearchActivityAdapter(
    private val context: Context,
    private val content: MutableList<Content?>,
    private val contentClickListener: ContentClickListener
) : RecyclerView.Adapter<SearchActivityAdapter.ViewHolder>() {

    companion object {
        private const val MARGIN_SIZE = 10
        private const val COVER_IMAGE_WIDTH = 400
        private const val COVER_IMAGE_HEIGHT = 560
    }

    interface ContentClickListener {
        fun onContentClicked(content: Content)
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.findViewById<ImageView>(R.id.ivContentCardCover)
        private val textView = itemView.findViewById<TextView>(R.id.tvContentCardTitle)

        fun bind(position: Int) {
            val item: Content? = content[position]
            val coverUrl = item?.getCoverImageUrl()
            if (coverUrl != "no_image") {
                Picasso.get().load(coverUrl).resize(
                    COVER_IMAGE_WIDTH,
                    COVER_IMAGE_HEIGHT
                ).into(imageView);
            } else {
                imageView.setImageResource(R.drawable.unwind_ps_logo_2)
                imageView.layoutParams.width =
                    COVER_IMAGE_WIDTH
                imageView.layoutParams.height =
                    COVER_IMAGE_HEIGHT
            }
            textView.text = item?.getTitle()

            imageView.setOnClickListener{
                if (item != null) {
                    contentClickListener.onContentClicked(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.content_card, parent, false)
        val layoutParams =
            view.findViewById<CardView>(R.id.cvContentCard).layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.setMargins(
            MARGIN_SIZE,
            MARGIN_SIZE,
            MARGIN_SIZE,
            MARGIN_SIZE
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return content.size
    }
}