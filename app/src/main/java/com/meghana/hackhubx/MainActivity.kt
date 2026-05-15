package com.meghana.hackhubx

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.meghana.hackhubx.adapter.HackathonAdapter
import com.meghana.hackhubx.databinding.ActivityMainBinding
import com.meghana.hackhubx.model.Application
import com.meghana.hackhubx.model.Hackathon
import com.meghana.hackhubx.model.User
import com.meghana.hackhubx.ui.dashboard.ApplicationsActivity
import com.meghana.hackhubx.ui.dashboard.ProfileActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding:
            ActivityMainBinding

    private lateinit var auth: FirebaseAuth

    private lateinit var firestore:
            FirebaseFirestore

    private val appliedHackathons =
        mutableSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        firestore =
            FirebaseFirestore.getInstance()

        loadUserData()

        loadAppliedHackathons()

        binding.btnMyApplications
            .setOnClickListener {

                startActivity(

                    Intent(
                        this,
                        ApplicationsActivity::class.java
                    )
                )
            }

        binding.btnProfile
            .setOnClickListener {

                startActivity(

                    Intent(
                        this,
                        ProfileActivity::class.java
                    )
                )
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

    private fun loadAppliedHackathons() {

        val uid =
            auth.currentUser?.uid ?: return

        firestore.collection("applications")

            .whereEqualTo(
                "userId",
                uid
            )

            .get()

            .addOnSuccessListener { result ->

                appliedHackathons.clear()

                for (document in result) {

                    val title =
                        document.getString(
                            "hackathonTitle"
                        )

                    if (title != null) {

                        appliedHackathons.add(
                            title
                        )
                    }
                }

                setupHackathons()
            }
    }

    private fun setupHackathons() {

        val hackathonList =
            mutableListOf<Hackathon>()

        val adapter =
            HackathonAdapter(

                hackathonList,

                appliedHackathons

            ) { hackathon ->

                applyToHackathon(hackathon)
            }

        binding.recyclerHackathons.layoutManager =
            LinearLayoutManager(this)

        binding.recyclerHackathons.adapter =
            adapter

        firestore.collection("hackathons")
            .get()

            .addOnSuccessListener { result ->

                hackathonList.clear()

                for (document in result) {

                    val hackathon =
                        document.toObject(
                            Hackathon::class.java
                        )

                    hackathonList.add(hackathon)
                }

                adapter.notifyDataSetChanged()
            }
    }

    private fun applyToHackathon(
        hackathon: Hackathon
    ) {

        val uid =
            auth.currentUser?.uid ?: return

        firestore.collection("applications")

            .whereEqualTo(
                "userId",
                uid
            )

            .whereEqualTo(
                "hackathonTitle",
                hackathon.title
            )

            .get()

            .addOnSuccessListener { result ->

                if (!result.isEmpty) {

                    android.widget.Toast.makeText(
                        this,
                        "Already Applied",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()

                } else {

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

                            appliedHackathons.add(
                                hackathon.title
                            )

                            setupHackathons()

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
    }
}