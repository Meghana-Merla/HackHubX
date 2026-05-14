package com.meghana.hackhubx.ui.auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.meghana.hackhubx.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding:
            ActivityRegisterBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {

            val name =
                binding.etName.text.toString().trim()

            val college =
                binding.etCollege.text.toString().trim()

            val email =
                binding.etEmail.text.toString().trim()

            val password =
                binding.etPassword.text.toString().trim()

            val confirmPassword =
                binding.etConfirmPassword.text
                    .toString()
                    .trim()

            when {

                name.isEmpty() -> {

                    showToast("Enter name")
                }

                college.isEmpty() -> {

                    showToast("Enter college name")
                }

                email.isEmpty() -> {

                    showToast("Enter email")
                }

                password.isEmpty() -> {

                    showToast("Enter password")
                }

                confirmPassword.isEmpty() -> {

                    showToast("Confirm password")
                }

                password != confirmPassword -> {

                    showToast(
                        "Passwords do not match"
                    )
                }

                password.length < 6 -> {

                    showToast(
                        "Password must be at least 6 characters"
                    )
                }

                else -> {

                    registerUser(
                        email,
                        password
                    )
                }
            }
        }
    }

    private fun registerUser(
        email: String,
        password: String
    ) {

        auth.createUserWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener {

            if (it.isSuccessful) {

                showToast(
                    "Registration Successful"
                )

                finish()

            } else {

                showToast(
                    it.exception?.message.toString()
                )
            }
        }
    }

    private fun showToast(message: String) {

        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}