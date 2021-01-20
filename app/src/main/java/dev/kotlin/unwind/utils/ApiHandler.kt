package dev.kotlin.unwind.utils

import android.content.Context
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import dev.kotlin.unwind.SearchActivityAdapter
import dev.kotlin.unwind.models.Content
import dev.kotlin.unwind.models.ContentType
import dev.kotlin.unwind.models.Movies
import dev.kotlin.unwind.models.TvShow
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ApiHandler {

    companion object{
        private const val NO_GENRES = "no_genres"
        private const val NO_IMAGE = "no_image"
        private const val NO_NAME = "no_name"
        private const val NO_SCORE = "no_score"
        private const val NO_ID = -1

    }

    fun tvContentSearch(
        context: Context,
        searchString: String,
        content: MutableList<Content?>,
        tvContentSearchFeedback: TextView,
        adapter: SearchActivityAdapter,
        contentType: ContentType
    ) {
        val queue = Volley.newRequestQueue(context)
        val url = "https://api.tvmaze.com/search/shows?q=$searchString"

        val jsonRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener {
                content.clear()
                if (it.length() != 0) {
                    iterateSearchResult(it, tvContentSearchFeedback, content, contentType)
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
            genres.add(NO_GENRES)
        }
        return TvShow(contentId, title, smallImage, largeImage, genres)
    }


    fun movieContentSearch(
        context: Context,
        searchString: String,
        content: MutableList<Content?>,
        movieContentSearchFeedback: TextView,
        adapter: SearchActivityAdapter,
        contentType: ContentType
    ){
        val queue = Volley.newRequestQueue(context)
        val url = "https://www.omdbapi.com/?s=$searchString&apikey=e8031205"

        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener {
                content.clear()
                if (it.getString("Response").equals("True")){
                    val jasonResponse: JSONArray = it.getJSONArray("Search")
                    iterateSearchResult(jasonResponse, movieContentSearchFeedback, content, contentType)
                } else {
                    movieContentSearchFeedback.text = "No result"
                    content.clear()
                }
                adapter.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                movieContentSearchFeedback.text = error.message
            }
        )
        queue.add(jsonRequest)
    }

    private fun iterateSearchResult(
        jasonResponse: JSONArray,
        movieContentSearchFeedback: TextView,
        content: MutableList<Content?>,
        contentType: ContentType

    ) {


        for (i in 0 until jasonResponse.length()) {
            val contentItem = parseJsonObjectToContent(jasonResponse.getJSONObject(i), contentType)
            movieContentSearchFeedback.text = ""
            content.add(contentItem)
        }
    }

    private fun parseJsonObjectToContent(jsonObject: JSONObject?, contentType: ContentType): Content? {
        val contentItem: Content
        when (contentType) {
            ContentType.TV_SHOW -> contentItem = parseJsonObjectToTvShow(jsonObject) as TvShow
            ContentType.MOVIE -> contentItem = parseJsonObjectToMovie(jsonObject) as Movies
            else -> contentItem = parseJsonObjectToMovie(jsonObject) as Movies
        }
        return contentItem
    }


    private fun parseJsonObjectToMovie(movie: JSONObject?): Movies? {
        val title: String
        var poster: String
        var metaScore: String


        title = try {
            movie?.getString("Title").toString()
        } catch (e: JSONException) {
            NO_NAME
        }

        metaScore = try {
            movie?.getString("Metascore").toString()
        } catch (e: JSONException) {
            NO_SCORE
        }

        poster = try {
            movie?.getString("Poster").toString()
        } catch (e: JSONException) {
            NO_IMAGE
        }

        /*if (poster != NO_IMAGE) {
            poster = "https://" + poster.substring(6)
        }*/




        return Movies(title, poster, metaScore)
    }

    fun searchType(
        context: Context,
        contentType: ContentType,
        content: MutableList<Content?>,
        searchString: String,
        tvContentSearchFeedback: TextView,
        adapter: SearchActivityAdapter
    ) {
        when(contentType){
            ContentType.MOVIE -> movieContentSearch(context, searchString, content, tvContentSearchFeedback, adapter, contentType)
            ContentType.TV_SHOW -> tvContentSearch(context, searchString, content, tvContentSearchFeedback, adapter, contentType)
        }
    }
}
