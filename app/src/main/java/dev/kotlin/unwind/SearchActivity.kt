package dev.kotlin.unwind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class SearchActivity : AppCompatActivity() {

    private lateinit var etSearchField: EditText
    private lateinit var btnSearchContent: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }
}