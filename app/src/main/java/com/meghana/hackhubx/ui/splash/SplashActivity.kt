package com.meghana.hackhubx.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.meghana.hackhubx.MainActivity
import com.meghana.hackhubx.R
import com.meghana.hackhubx.ui.auth.LoginActivity
import com.meghana.hackhubx.ui.dashboard.OrganizerDashboardActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var firestore:
            FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        auth = FirebaseAuth.getInstance()

        firestore =
            FirebaseFirestore.getInstance()

        Handler(Looper.getMainLooper())
            .postDelayed({

                val currentUser =
                    auth.currentUser

                if (currentUser != null) {

                    val uid =
                        currentUser.uid

                    firestore.collection("users")
                        .document(uid)
                        .get()

                        .addOnSuccessListener {

                            val role =
                                it.getString("role")

                            if (role == "organizer") {

                                startActivity(

                                    Intent(
                                        this,
                                        OrganizerDashboardActivity::class.java
                                    )
                                )

                            } else {

                                startActivity(

                                    Intent(
                                        this,
                                        MainActivity::class.java
                                    )
                                )
                            }

                            finish()
                        }

                } else {

                    startActivity(

                        Intent(
                            this,
                            LoginActivity::class.java
                        )
                    )

                    finish()
                }

            }, 2000)
    }
}