package dev.kotlin.unwind.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import dev.kotlin.unwind.MainRegister
import dev.kotlin.unwind.R
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // TODO - Add button - Edit Profile

        auth = FirebaseAuth.getInstance()

        btnSignOut.setOnClickListener {
            auth.signOut()

            var intent = Intent(this, MainRegister::class.java)
            startActivity(intent)
        }

        btnShowProfile.setOnClickListener{
            var intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        btnTestContentSearch.setOnClickListener {
            var intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)

        }
    }


}