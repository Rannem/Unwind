package dev.kotlin.unwind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ContentViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_view)
    }
}