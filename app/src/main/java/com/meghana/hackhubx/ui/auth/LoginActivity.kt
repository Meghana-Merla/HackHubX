package com.meghana.hackhubx.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.meghana.hackhubx.MainActivity
import com.meghana.hackhubx.databinding.ActivityLoginBinding
import com.meghana.hackhubx.ui.auth.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding:
            ActivityLoginBinding

    private lateinit var auth:
            FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityLoginBinding.inflate(
                layoutInflater
            )

        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {

            val email =
                binding.etEmail.text
                    .toString()
                    .trim()

            val password =
                binding.etPassword.text
                    .toString()
                    .trim()

            if (
                email.isEmpty() ||
                password.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Fill all fields",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                loginUser(
                    email,
                    password
                )
            }
        }

        binding.tvRegister.setOnClickListener {

            startActivity(

                Intent(
                    this,
                    RegisterActivity::class.java
                )
            )
        }
    }

    private fun loginUser(
        email: String,
        password: String
    ) {

        startLoading()

        auth.signInWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener {

            if (it.isSuccessful) {

                val uid =
                    auth.currentUser?.uid
                        ?: return@addOnCompleteListener

                com.google.firebase.firestore
                    .FirebaseFirestore
                    .getInstance()

                    .collection("users")

                    .document(uid)

                    .get()

                    .addOnSuccessListener { document ->

                        stopLoading()

                        val role =
                            document.getString(
                                "role"
                            )

                        Toast.makeText(
                            this,
                            "Login Successful",
                            Toast.LENGTH_SHORT
                        ).show()

                        if (role == "organizer") {

                            startActivity(

                                Intent(
                                    this,
                                    com.meghana.hackhubx
                                        .ui.dashboard
                                        .OrganizerDashboardActivity::class.java
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

                stopLoading()

                Toast.makeText(
                    this,
                    it.exception?.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun startLoading() {

        binding.btnLogin.isEnabled =
            false

        binding.btnLogin.text =
            "Logging in..."
    }

    private fun stopLoading() {

        binding.btnLogin.isEnabled =
            true

        binding.btnLogin.text =
            "Login"
    }
}