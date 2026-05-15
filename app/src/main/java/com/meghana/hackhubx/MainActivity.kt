package com.meghana.hackhubx

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.meghana.hackhubx.databinding.ActivityMainBinding
import com.meghana.hackhubx.model.User
import com.meghana.hackhubx.ui.auth.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding:
            ActivityMainBinding

    private lateinit var auth: FirebaseAuth

    private lateinit var firestore:
            FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        firestore =
            FirebaseFirestore.getInstance()

        loadUserData()

        binding.btnLogout.setOnClickListener {

            auth.signOut()

            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )

            finish()
        }
    }

    private fun loadUserData() {

        val uid =
            auth.currentUser?.uid ?: return

        firestore.collection("users")
            .document(uid)
            .get()

            .addOnSuccessListener {

                val user =
                    it.toObject(User::class.java)

                binding.tvName.text =
                    "Welcome ${user?.name} 👋"

                binding.tvCollege.text =
                    user?.college
            }
    }
}