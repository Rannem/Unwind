package dev.kotlin.unwind.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.kotlin.unwind.R
import dev.kotlin.unwind.adapters.SearchActivityAdapter
import dev.kotlin.unwind.models.Content
import dev.kotlin.unwind.models.ContentType
import dev.kotlin.unwind.utils.ApiHandler

class SearchActivity : AppCompatActivity() {

    private lateinit var etSearchField: EditText
    private lateinit var btnSearchContent: Button
    private lateinit var rvTvContent: RecyclerView

    private lateinit var adapter: SearchActivityAdapter
    private lateinit var content: MutableList<Content?>
    private lateinit var apiHandler: ApiHandler

    private lateinit var tvContentSearchFeedback: TextView
    private lateinit var movieContentSearchFeedback: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        etSearchField = findViewById(R.id.etSearchField)
        btnSearchContent = findViewById(R.id.btnSearchContent)
        rvTvContent = findViewById(R.id.rvTvContent)
        tvContentSearchFeedback = findViewById(R.id.tvContentSearchFeedback)
        movieContentSearchFeedback = findViewById(R.id.movieContentSearchFeedback)

        btnSearchContent.setOnClickListener {
            searchContent()
        }

        content = mutableListOf()
        adapter = SearchActivityAdapter(this, content, object: SearchActivityAdapter.ContentClickListener {
            override fun onContentClicked(contentId: Int, contentType: ContentType) {
                val intent = Intent(this@SearchActivity, ContentViewActivity::class.java)
                intent.putExtra("contentId", contentId)
                startActivity(intent)
            }
        })
        apiHandler = ApiHandler()

        rvTvContent.adapter = adapter
        rvTvContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    }

    private fun searchContent() {
        //implement a search for each type of content
        val rgCategory = findViewById<RadioGroup>(R.id.sa_rg_options)
        val intSelectButton: Int = rgCategory.checkedRadioButtonId
        val rbContentType = findViewById<RadioButton>(intSelectButton)
        var contentType : ContentType = ContentType.NOVALUE

        when(rbContentType.text) {
            "Tv Show" -> contentType = ContentType.TV_SHOW
            "Movie" -> contentType = ContentType.MOVIE
            else -> println("Something went wrong, blame the other guy!")
        }

        apiHandler.searchType(this, contentType, content, etSearchField.text.toString(), tvContentSearchFeedback, adapter)

       /* when(rbContentType.text) {
            "Tv Show"-> apiHandler.tvContentSearch(this, etSearchField.text.toString(), content, tvContentSearchFeedback, adapter)
            "Movie"-> apiHandler.movieContentSearch(this, etSearchField.text.toString(), content, movieContentSearchFeedback, adapter)
            else -> println("Something went wrong, blame the other guy!")
        }*/


    }


}