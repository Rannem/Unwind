package dev.kotlin.unwind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import dev.kotlin.unwind.models.Content
import dev.kotlin.unwind.models.TvShow
import org.json.JSONException
import org.json.JSONObject

class SearchActivity : AppCompatActivity() {

    private lateinit var etSearchField: EditText
    private lateinit var btnSearchContent: Button
    private lateinit var rvTvContent: RecyclerView

    private lateinit var adapter: SearchActivityAdapter
    private lateinit var content: MutableList<Content?>

    //delete testing textView!!!
    private lateinit var tvTestingPurpose: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        etSearchField = findViewById(R.id.etSearchField)
        btnSearchContent = findViewById(R.id.btnSearchContent)
        rvTvContent = findViewById(R.id.rvTvContent)
        //delete testing purpose!!
        tvTestingPurpose = findViewById(R.id.tvTestingPurpose)

        btnSearchContent.setOnClickListener {
            searchContent()
        }

        content = mutableListOf()
        adapter = SearchActivityAdapter(this, content)

        rvTvContent.adapter = adapter
        rvTvContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)

    }

    private fun searchContent() {
        //given input in the search field should handle search for content
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.tvmaze.com/search/shows?q=${etSearchField.text}"
        var show: TvShow?

        val jsonRequest = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener {
                content.clear()
                if (it.length() != 0){
                    for (i in 0 until it.length()){
                        show = parseJsonObjectToTvShow(it.getJSONObject(i))
                        tvTestingPurpose.text = ""
                        content.add(show)
                    }
                } else {
                    tvTestingPurpose.text = "No result"
                    content.clear()
                }
                adapter.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                tvTestingPurpose.text = error.message
            }
        )
        queue.add(jsonRequest)
    }

    private fun parseJsonObjectToTvShow(jsonObject: JSONObject?): TvShow? {
        val show = jsonObject?.getJSONObject("show")
        val contentId: Int
        val title: String
        val genres = mutableListOf<String>()
        var smallImage: String
        var largeImage: String


        try {
            contentId = show?.getInt("id")!!
        } catch (e: JSONException) {
            return null
        }

        title = try {
            show?.getString("name")
        } catch (e: JSONException) {
            "no_name"
        }

        smallImage = try {
            show?.getJSONObject("image")?.getString("medium").toString()
        } catch (e: JSONException) {
            "no_image"
        }

        if (!smallImage.equals("no_image")){
            smallImage = "https://" + smallImage.substring(6)
        }

        largeImage = try {
            show?.getJSONObject("image")?.getString("original").toString()
        } catch (e: JSONException) {
            "no_image"
        }

        if (!largeImage.equals("no_image")){
            largeImage = "https://" + largeImage.substring(6)
        }

        try {
            val jsonArrayGenres = show?.getJSONArray("genres")
            if (jsonArrayGenres != null) {
                for (i in 0 until jsonArrayGenres.length()) {
                    genres.add(jsonArrayGenres.getString(i))
                }
            }

        } catch (e: JSONException) {
            genres.add("undefined")
        }
        return TvShow(contentId, title, smallImage, largeImage, genres)
    }
}