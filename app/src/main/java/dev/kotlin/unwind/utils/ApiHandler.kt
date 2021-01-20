package dev.kotlin.unwind.utils

import android.content.Context
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import dev.kotlin.unwind.SearchActivityAdapter
import dev.kotlin.unwind.models.Content
import dev.kotlin.unwind.models.TvShow
import org.json.JSONException
import org.json.JSONObject

class ApiHandler {

    companion object{
        private const val NO_IMAGE = "no_image"
        private const val NO_NAME = "no_name"

    }

    fun tvContentSearch(
        context: Context,
        searchString: String,
        content: MutableList<Content?>,
        tvContentSearchFeedback: TextView,
        adapter: SearchActivityAdapter
    ) {
        val queue = Volley.newRequestQueue(context)
        val url = "https://api.tvmaze.com/search/shows?q=$searchString"
        var show: TvShow?

        val jsonRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener {
                content.clear()
                if (it.length() != 0) {
                    for (i in 0 until it.length()) {
                        show = parseJsonObjectToTvShow(it.getJSONObject(i))
                        tvContentSearchFeedback.text = ""
                        content.add(show)
                    }
                } else {
                    tvContentSearchFeedback.text = "No result"
                    content.clear()
                }
                adapter.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                tvContentSearchFeedback.text = error.message
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
            NO_NAME
        }

        smallImage = try {
            show?.getJSONObject("image")?.getString("medium").toString()
        } catch (e: JSONException) {
           NO_IMAGE
        }

        if (smallImage != NO_IMAGE) {
            smallImage = "https://" + smallImage.substring(6)
        }

        largeImage = try {
            show?.getJSONObject("image")?.getString("original").toString()
        } catch (e: JSONException) {
            NO_IMAGE
        }

        if (largeImage != NO_IMAGE) {
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
