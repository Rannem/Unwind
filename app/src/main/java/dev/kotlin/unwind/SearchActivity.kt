package dev.kotlin.unwind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import dev.kotlin.unwind.models.TvShow
import org.json.JSONObject

class SearchActivity : AppCompatActivity() {

    private lateinit var etSearchField: EditText
    private lateinit var btnSearchContent: Button
    private lateinit var rvTvContent: RecyclerView
    //delete testing textview!!!
    private lateinit var tvTestingPurpose: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        etSearchField = findViewById(R.id.etSearchField)
        btnSearchContent = findViewById(R.id.btnSearchContent)
        rvTvContent = findViewById(R.id.rvTvContent)
        //delete testing purpose!!
        tvTestingPurpose = findViewById(R.id.tvTestingPurpose)

        btnSearchContent.setOnClickListener{
            searchContent()
        }
    }

    private fun searchContent() {
        //given input in the search field should handle search for content
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.tvmaze.com/search/shows?q=girls"

        val jsonRequest = JsonArrayRequest(Request.Method.GET, url, null,
        Response.Listener{
            val i = 0
            val show = parseJsonObjectToTvShow(it.getJSONObject(i))
        },
            Response.ErrorListener {error ->
                tvTestingPurpose.text = error.message
            }
        )
        queue.add(jsonRequest)
    }

    private fun parseJsonObjectToTvShow(jsonObject: JSONObject?): TvShow? {
        val show = jsonObject?.getJSONObject("show")
        return show?.getInt("id")?.let { TvShow(it, show.getString("name")) }
    }
}