package dev.kotlin.unwind.models

import java.io.Serializable

interface Content: Serializable
{
    val contentType: ContentType
    fun getTitle(): String
    fun getContentId(): String
    fun getCoverImageUrl(): String
    fun getIMBDRating(): String
    fun getPlot(): String
    fun getRuntime(): String
    fun getGenre(): String

}