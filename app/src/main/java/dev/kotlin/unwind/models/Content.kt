package dev.kotlin.unwind.models

import java.io.Serializable

interface Content: Serializable
{
    val contentType: ContentType
    fun getTitle(): String
    fun getContentId(): Int
    fun getCoverImageUrl(): String
}