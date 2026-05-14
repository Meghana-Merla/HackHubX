package com.meghana.hackhubx.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.meghana.hackhubx.MainActivity
import com.meghana.hackhubx.R
import com.meghana.hackhubx.ui.auth.LoginActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        auth = FirebaseAuth.getInstance()

        Handler(Looper.getMainLooper()).postDelayed({

            val currentUser = auth.currentUser

            if (currentUser != null) {

                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    )
                )

            } else {

                startActivity(
                    Intent(
                        this,
                        LoginActivity::class.java
                    )
                )
            }

            finish()

        }, 2000)
    }
}