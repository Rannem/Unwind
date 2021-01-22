package dev.kotlin.unwind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()

        // TODO Set edit profile button +++

        /*
        fun editProfileBtn.setOnClickListener(view: View) {
            var intent = Intent(this, CreateProfile::class.java)
            startActivity(intent)
        }
   */

    }
}