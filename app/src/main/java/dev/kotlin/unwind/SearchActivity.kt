package dev.kotlin.unwind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.kotlin.unwind.models.Content
import dev.kotlin.unwind.utils.ApiHandler

class SearchActivity : AppCompatActivity() {

    private lateinit var etSearchField: EditText
    private lateinit var btnSearchContent: Button
    private lateinit var rvTvContent: RecyclerView

    private lateinit var adapter: SearchActivityAdapter
    private lateinit var content: MutableList<Content?>
    private lateinit var apiHandler: ApiHandler

    private lateinit var tvContentSearchFeedback: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        etSearchField = findViewById(R.id.etSearchField)
        btnSearchContent = findViewById(R.id.btnSearchContent)
        rvTvContent = findViewById(R.id.rvTvContent)
        tvContentSearchFeedback = findViewById(R.id.tvContentSearchFeedback)

        btnSearchContent.setOnClickListener {
            searchContent()
        }

        content = mutableListOf()
        adapter = SearchActivityAdapter(this, content)
        apiHandler = ApiHandler()

        rvTvContent.adapter = adapter
        rvTvContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    }

    private fun searchContent() {
        //implement a search for each type of content
        apiHandler.tvContentSearch(this, etSearchField.text.toString(), content, tvContentSearchFeedback, adapter)

    }


}