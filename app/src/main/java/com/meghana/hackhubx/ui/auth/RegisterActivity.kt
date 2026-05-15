package com.meghana.hackhubx.ui.auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.meghana.hackhubx.databinding.ActivityRegisterBinding
import com.meghana.hackhubx.model.User

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding:
            ActivityRegisterBinding

    private lateinit var auth:
            FirebaseAuth

    private lateinit var firestore:
            FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityRegisterBinding.inflate(
                layoutInflater
            )

        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        firestore =
            FirebaseFirestore.getInstance()

        binding.btnRegister.setOnClickListener {

            val name =
                binding.etName.text
                    .toString()
                    .trim()

            val college =
                binding.etCollege.text
                    .toString()
                    .trim()

            val email =
                binding.etEmail.text
                    .toString()
                    .trim()

            val password =
                binding.etPassword.text
                    .toString()
                    .trim()

            val confirmPassword =
                binding.etConfirmPassword.text
                    .toString()
                    .trim()

            val role = when {

                binding.radioCandidate.isChecked ->
                    "candidate"

                binding.radioOrganizer.isChecked ->
                    "organizer"

                else -> ""
            }

            when {

                name.isEmpty() -> {

                    showToast(
                        "Enter name"
                    )
                }

                college.isEmpty() -> {

                    showToast(
                        "Enter college name"
                    )
                }

                email.isEmpty() -> {

                    showToast(
                        "Enter email"
                    )
                }

                password.isEmpty() -> {

                    showToast(
                        "Enter password"
                    )
                }

                confirmPassword.isEmpty() -> {

                    showToast(
                        "Confirm password"
                    )
                }

                password != confirmPassword -> {

                    showToast(
                        "Passwords do not match"
                    )
                }

                role.isEmpty() -> {

                    showToast(
                        "Select role"
                    )
                }

                password.length < 6 -> {

                    showToast(
                        "Password must be at least 6 characters"
                    )
                }

                else -> {

                    registerUser(
                        name,
                        college,
                        email,
                        password,
                        role
                    )
                }
            }
        }
    }

    private fun registerUser(
        name: String,
        college: String,
        email: String,
        password: String,
        role: String
    ) {

        startLoading()

        auth.createUserWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener {

            if (it.isSuccessful) {

                val uid =
                    auth.currentUser?.uid ?: ""

                val user = User(

                    uid = uid,

                    name = name,

                    college = college,

                    email = email,

                    role = role
                )

                firestore.collection("users")
                    .document(uid)
                    .set(user)

                    .addOnSuccessListener {

                        stopLoading()

                        showToast(
                            "Registration Successful"
                        )

                        finish()
                    }

                    .addOnFailureListener {

                        stopLoading()

                        showToast(
                            it.message.toString()
                        )
                    }

            } else {

                stopLoading()

                showToast(
                    it.exception?.message.toString()
                )
            }
        }
    }

    private fun startLoading() {

        binding.btnRegister.isEnabled =
            false

        binding.btnRegister.text =
            "Creating Account..."
    }

    private fun stopLoading() {

        binding.btnRegister.isEnabled =
            true

        binding.btnRegister.text =
            "Register"
    }

    private fun showToast(
        message: String
    ) {

        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}