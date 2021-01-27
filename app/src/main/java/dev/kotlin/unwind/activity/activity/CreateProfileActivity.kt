package dev.kotlin.unwind.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import dev.kotlin.unwind.R

class CreateProfileActivity : AppCompatActivity() {

    // private lateinit var btnShowProfile : Button
    var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)

        val btnShowProfile = findViewById<View>(R.id.cp_btn_save_profile) as Button


        fun createProfile(firstName: String, lastName: String, bio: String) {
            var intent = Intent(this, CreateProfileActivity::class.java)
            startActivity(intent)
        }
    }
}