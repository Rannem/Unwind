package dev.kotlin.unwind.models

interface Content
{
    val contentType: ContentType
    fun getTitle()
    fun getContentId()
}