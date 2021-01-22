package dev.kotlin.unwind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CreateProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)



        // TODO Create functioning profile

        // Create Users
        // Create User repo
        // Create Material Theme
        // Get user credentials in Profile

        
        fun createProfile(firstName: String, lastName: String, bio: String){
            var intent = Intent(this, CreateProfileActivity::class.java)
            startActivity(intent)
        }
    }
}