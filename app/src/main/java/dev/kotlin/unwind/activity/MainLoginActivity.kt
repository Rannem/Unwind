package dev.kotlin.unwind.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.kotlin.unwind.R

class MainLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_login)
    }
}