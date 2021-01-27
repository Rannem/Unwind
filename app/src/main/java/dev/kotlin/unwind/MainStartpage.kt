package dev.kotlin.unwind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dev.kotlin.unwind.activity.DashboardActivity
import kotlinx.android.synthetic.main.activity_main_startpage.*

class MainStartpage : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth

    private val TAG = "Firestore"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_startpage)

        auth = FirebaseAuth.getInstance()
        // Access a Cloud Firestore instance from your Activity
        val db = Firebase.firestore

        db.collection("profiles")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }


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