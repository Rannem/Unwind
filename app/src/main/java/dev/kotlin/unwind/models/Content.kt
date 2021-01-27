package dev.kotlin.unwind.models

import android.net.Uri

interface Content
{
    val contentType: ContentType
    fun getTitle(): String
    fun getContentId(): Int
    fun getCoverImageUrl(): String
}