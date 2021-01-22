package dev.kotlin.unwind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import dev.kotlin.unwind.activity.DashboardActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainRegister : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()



        btnRegister.setOnClickListener {
            if (editEmail.text.trim().toString().isNotEmpty() || editPassword.text.trim()
                    .isNotEmpty()
            ) {
                createUser(editEmail.text.trim().toString(), editPassword.text.trim().toString())

            } else {

                Toast.makeText(this, "Input required", Toast.LENGTH_SHORT).show()

            }
        }
    }

        fun createUser(email: String, password: String) {

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {
                        Log.e("Task Message", "Succcessfull")

                        var intent = Intent(this, DashboardActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.e("Task Message", "Failed"+task.exception)
                    }

                }

        }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser

        if (user != null) {
            var intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
    }

    }
