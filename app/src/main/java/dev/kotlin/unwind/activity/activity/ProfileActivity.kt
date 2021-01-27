package dev.kotlin.unwind.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.firebase.auth.FirebaseAuth
import dev.kotlin.unwind.R

class ProfileActivity : AppCompatActivity() {

/*
    private lateinit var tvSpUserName: TextView
    private lateinit var tvSpUserFirstName: TextView
    private lateinit var tvSpUserLastName: TextView
    private lateinit var tvSpUserDob: Spinner
    private lateinit var tvSpUserBio: TextView
    private lateinit var tvSpUserGender: TextView
    private lateinit var tvSpUserCountry: TextView
    private lateinit var ivSpUserProfileImage: ImageView
    private lateinit var btnSpEditProfilwPhoto: FloatingActionButton
    private lateinit var tvSpLogoutLink: TextView
    private lateinit var btnSpEditProfile: TextView


 */

    /*
    profile ids:
    layoutmain : userProfileMainLayout
    sp_tv_logout_link
    logo unwind_logo_1 image v
    loatingActionButton float btn
    tv_sp_profile_image image
    tv_sp_profile_title
    tv_sp_first_name
    tv_sp_profile_title
    tv_sp_first_name
    tv_sp_last_name
    tv_sp_user_dob
    tv_sp_user_gender
    sp_btn_dashboard
    tv_sp_username

     */
    // Access a Cloud Firestore instance from your Activity

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    //db = FirebaseDatabase.getInstance().getReference("Users")
        auth = FirebaseAuth.getInstance()
        update()
    }

    private fun update() {

        val user = auth.currentUser
        val uid = user!!.uid

    }

}

        // TODO Set edit profile button +++

        /*
        fun editProfileBtn.setOnClickListener(view: View) {
            var intent = Intent(this, CreateProfile::class.java)
            startActivity(intent)
        }

         */



