package dev.kotlin.unwind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main_startpage.*

class MainStartpage : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_startpage)

        auth = FirebaseAuth.getInstance()


        btnMainPageLogin.setOnClickListener {
            var intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
        btnMainPageRegister.setOnClickListener {
            var intent = Intent(this, MainRegister::class.java)
            startActivity(intent)
        }
    }
}