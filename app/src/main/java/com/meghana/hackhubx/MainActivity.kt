package com.meghana.hackhubx

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.meghana.hackhubx.adapter.HackathonAdapter
import com.meghana.hackhubx.databinding.ActivityMainBinding
import com.meghana.hackhubx.model.Hackathon
import com.meghana.hackhubx.model.User
import com.meghana.hackhubx.ui.auth.LoginActivity
import com.meghana.hackhubx.model.Application

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

        setupHackathons()

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

    private fun setupHackathons() {

        val hackathonList = listOf(

            Hackathon(
                "AI Hackathon 2026",
                "₹50,000",
                "2-4",
                "May 25"
            ),

            Hackathon(
                "Web3 Buildathon",
                "₹1,00,000",
                "1-3",
                "June 2"
            ),

            Hackathon(
                "Open Source Sprint",
                "₹25,000",
                "2-5",
                "May 30"
            )
        )

        val adapter =
            HackathonAdapter(
                hackathonList
            ) { hackathon ->

                applyToHackathon(hackathon)
            }
        binding.recyclerHackathons.layoutManager =
            LinearLayoutManager(this)

        binding.recyclerHackathons.adapter =
            adapter
    }
    private fun applyToHackathon(
        hackathon: Hackathon
    ) {

        val uid =
            auth.currentUser?.uid ?: return

        val application = Application(

            userId = uid,

            hackathonTitle =
                hackathon.title,

            timestamp =
                System.currentTimeMillis()
        )

        firestore.collection("applications")
            .add(application)

            .addOnSuccessListener {

                android.widget.Toast.makeText(
                    this,
                    "Applied Successfully",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            }

            .addOnFailureListener {

                android.widget.Toast.makeText(
                    this,
                    it.message,
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            }
    }
}