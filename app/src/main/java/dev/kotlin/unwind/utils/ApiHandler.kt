package dev.kotlin.unwind.utils

import android.content.Context
import android.util.Log
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import dev.kotlin.unwind.adapters.ContentViewActivityAdapter
import dev.kotlin.unwind.adapters.SearchActivityAdapter
import dev.kotlin.unwind.models.Content
import dev.kotlin.unwind.models.ContentType
import dev.kotlin.unwind.models.Movies
import dev.kotlin.unwind.models.TvShow
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

class ApiHandler {

    companion object {
        private const val TAG = "ApiHandler"
        private const val NO_GENRES = "no_genres"
        private const val NO_IMAGE = "no_image"
        private const val NO_NAME = "no_name"
        private const val NO_SCORE = "no_score"
        private const val NO_ID = "no_id"

    }

    private fun tvContentSearch(
        context: Context,
        searchString: String,
        content: MutableList<Content?>,
        tvContentSearchFeedback: TextView,
        adapter: SearchActivityAdapter
    ) {
        val queue = Volley.newRequestQueue(context)
        val url = "https://api.tvmaze.com/search/shows?q=$searchString"
        val contentType = ContentType.TV_SHOW

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
        val contentId: String
        val genres = mutableListOf<String>()
        var smallImage: String
        var largeImage: String


        try {
            contentId = jsonObject?.getInt("id").toString()
        } catch (e: JSONException) {
            return null
        }

        val title: String = try {
            jsonObject?.getString("name").toString()
        } catch (e: JSONException) {
            NO_NAME
        }

        smallImage = try {
            jsonObject?.getJSONObject("image")?.getString("medium").toString()
        } catch (e: JSONException) {
            NO_IMAGE
        }

        if (smallImage != NO_IMAGE) {
            smallImage = "https://" + smallImage.substring(6)
        }

        largeImage = try {
            jsonObject?.getJSONObject("image")?.getString("original").toString()
        } catch (e: JSONException) {
            NO_IMAGE
        }

        if (largeImage != NO_IMAGE) {
            largeImage = "https://" + largeImage.substring(6)
        }


        try {
            val jsonArrayGenres = jsonObject?.getJSONArray("genres")
            if (jsonArrayGenres != null) {
                for (i in 0 until jsonArrayGenres.length()) {
                    genres.add(jsonArrayGenres.getString(i))
                }
            }

        } catch (e: JSONException) {
            genres.add(NO_GENRES)
        }

        val IMBDRating = try {
            jsonObject?.getJSONObject("rating")?.getString("average").toString()
        } catch (e: JSONException) {
            NO_SCORE
        }
        val runtime: String = try {
            jsonObject?.getString("runtime").toString()
        } catch (e: JSONException) {
            NO_SCORE
        }
        val plot: String = try {
            jsonObject?.getString("summary").toString()
        } catch (e: JSONException) {
            NO_SCORE
        }



        return TvShow(contentId, title, smallImage, largeImage, IMBDRating, runtime,  genres, plot)
    }


    private fun movieContentSearch(
        context: Context,
        searchString: String,
        content: MutableList<Content?>,
        movieContentSearchFeedback: TextView,
        adapter: SearchActivityAdapter
    ) {
        val queue = Volley.newRequestQueue(context)
        val url = "https://www.omdbapi.com/?s=$searchString&apikey=e8031205"
        val contentType = ContentType.MOVIE

        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener {
                content.clear()
                if (it.getString("Response").equals("True")) {
                    val jasonResponse: JSONArray = it.getJSONArray("Search")
                    iterateSearchResult(
                        jasonResponse,
                        movieContentSearchFeedback,
                        content,
                        contentType
                    )
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

    private fun parseJsonObjectToContent(
        jsonObject: JSONObject?,
        contentType: ContentType
    ): Content? {
        val contentItem: Content
        when (contentType) {
            ContentType.TV_SHOW -> contentItem =
                parseJsonObjectToTvShow(jsonObject?.getJSONObject("show")) as TvShow
            ContentType.MOVIE -> contentItem = parseJsonObjectToMovie(jsonObject) as Movies
            else -> contentItem = parseJsonObjectToMovie(jsonObject) as Movies
        }
        return contentItem
    }


    private fun parseJsonObjectToMovie(movie: JSONObject?): Movies? {

        val contentId: String = try {
            movie?.getString("imdbID").toString()
        } catch (e: Exception) {
            NO_ID
        }

        val title: String = try {
            movie?.getString("Title").toString()
        } catch (e: JSONException) {
            NO_NAME
        }

        var poster: String = try {
            movie?.getString("Poster").toString()
        } catch (e: JSONException) {
            NO_IMAGE
        }

        var IMBDRating: String = try {
            movie?.getString("imdbRating").toString()
        } catch (e: JSONException) {
            NO_SCORE
        }

        var plot: String = try {
            movie?.getString("Plot").toString()
        } catch (e: JSONException) {
            NO_SCORE
        }

        var runtime: String = try {
            movie?.getString("Runtime").toString()
        } catch (e: JSONException) {
            NO_SCORE
        }

        var genre: String = try {
            movie?.getString("Genre").toString()
        } catch (e: JSONException) {
            NO_SCORE
        }

        return Movies(contentId, title, poster, IMBDRating, runtime, genre,  plot)
    }

    fun searchType(
        context: Context,
        contentType: ContentType,
        content: MutableList<Content?>,
        searchString: String,
        tvContentSearchFeedback: TextView,
        adapter: SearchActivityAdapter
    ) {
        when (contentType) {
            ContentType.MOVIE -> movieContentSearch(
                context,
                searchString,
                content,
                tvContentSearchFeedback,
                adapter
            )
            ContentType.TV_SHOW -> tvContentSearch(
                context,
                searchString,
                content,
                tvContentSearchFeedback,
                adapter
            )
        }
    }

    fun setTvShowById(
        context: Context,
        contentId: String,
        adapter: ContentViewActivityAdapter,
        content: MutableList<Content?>
    ): TvShow? {
        val queue = Volley.newRequestQueue(context)
        val url = "https://api.tvmaze.com/shows/$contentId"
        var tvShow: TvShow? = null
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener {
                tvShow = parseJsonObjectToTvShow(it)
                content.clear()
                content.add(tvShow)
                adapter.notifyDataSetChanged()
            },
            Response.ErrorListener {
                Log.e(TAG, it.message.toString())
            }
        )
        queue.add(jsonRequest)
        return tvShow
    }

    fun setMovieById(
        context: Context,
        contentId: String,
        adapter: ContentViewActivityAdapter,
        content: MutableList<Content?>
    ): Movies? {
        val queue = Volley.newRequestQueue(context)
        val url = "https://www.omdbapi.com/?i=$contentId&apikey=e8031205"
        val contentType = ContentType.MOVIE
        var movie: Movies? = null


        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener {
                movie = parseJsonObjectToMovie(it)
                content.clear()
                content.add(movie)
                adapter.notifyDataSetChanged()
            },
            Response.ErrorListener {
                Log.e(TAG, it.message.toString())
            }
        )
        queue.add(jsonRequest)
        return movie
    }
}
