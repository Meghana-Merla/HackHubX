package com.meghana.hackhubx.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.meghana.hackhubx.adapter.HackathonAdapter
import com.meghana.hackhubx.databinding.ActivityOrganizerDashboardBinding
import com.meghana.hackhubx.model.Hackathon
import androidx.appcompat.app.AlertDialog

class OrganizerDashboardActivity
    : AppCompatActivity() {

    private lateinit var binding:
            ActivityOrganizerDashboardBinding

    private lateinit var firestore:
            FirebaseFirestore

    private lateinit var auth:
            FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityOrganizerDashboardBinding
                .inflate(layoutInflater)

        setContentView(binding.root)

        firestore =
            FirebaseFirestore.getInstance()

        auth =
            FirebaseAuth.getInstance()

        setupMyHackathons()

        binding.btnAddHackathon
            .setOnClickListener {

                startActivity(

                    Intent(
                        this,
                        AddHackathonActivity::class.java
                    )
                )
            }

        binding.btnProfile.setOnClickListener {

            startActivity(

                Intent(
                    this,
                    ProfileActivity::class.java
                )
            )
        }
    }

    private fun setupMyHackathons() {

        val organizerId =
            auth.currentUser?.uid ?: return

        val hackathonList =
            mutableListOf<Hackathon>()

        val adapter =

            HackathonAdapter(

                hackathonList,

                emptySet(),

                null,

                { hackathon ->

                    AlertDialog.Builder(this)

                        .setTitle(
                            "Delete Hackathon"
                        )

                        .setMessage(
                            "Are you sure you want to delete this hackathon?"
                        )

                        .setPositiveButton(
                            "Delete"
                        ) { _, _ ->

                            deleteHackathon(
                                hackathon
                            )
                        }

                        .setNegativeButton(
                            "Cancel",
                            null
                        )

                        .show()
                },

                { hackathon ->

                    val intent =

                        Intent(
                            this,
                            AddHackathonActivity::class.java
                        )

                    intent.putExtra(
                        "title",
                        hackathon.title
                    )

                    intent.putExtra(
                        "prize",
                        hackathon.prize
                    )

                    intent.putExtra(
                        "teamSize",
                        hackathon.teamSize
                    )

                    intent.putExtra(
                        "deadline",
                        hackathon.deadline
                    )

                    intent.putExtra(
                        "documentId",
                        hackathon.documentId
                    )

                    startActivity(intent)
                },

                { hackathon ->
                    val intent =

                        Intent(
                            this,
                            ApplicantsActivity::class.java
                        )

                    intent.putExtra(
                        "hackathonTitle",
                        hackathon.title
                    )

                    startActivity(intent)
                }
            )

        binding.recyclerMyHackathons
            .layoutManager =
            LinearLayoutManager(this)

        binding.recyclerMyHackathons
            .adapter = adapter

        firestore.collection("hackathons")

            .whereEqualTo(
                "organizerId",
                organizerId
            )

            .get()

            .addOnSuccessListener { result ->

                hackathonList.clear()

                for (document in result) {

                    val hackathon =
                        document.toObject(
                            Hackathon::class.java
                        ).copy(

                            documentId =
                                document.id
                        )

                    firestore.collection("applications")

                        .whereEqualTo(
                            "hackathonTitle",
                            hackathon.title
                        )

                        .get()

                        .addOnSuccessListener { applications ->

                            val updatedHackathon =

                                hackathon.copy(

                                    applicantCount =
                                        applications.size()
                                )

                            hackathonList.add(
                                updatedHackathon
                            )

                            adapter.notifyDataSetChanged()
                        }
                }

                adapter.notifyDataSetChanged()

                if (hackathonList.isEmpty()) {

                    binding.tvEmptyState.visibility =
                        android.view.View.VISIBLE

                } else {

                    binding.tvEmptyState.visibility =
                        android.view.View.GONE
                }
            }
    }
    private fun deleteHackathon(
        hackathon: Hackathon
    ) {

        firestore.collection("hackathons")

            .whereEqualTo(
                "title",
                hackathon.title
            )

            .whereEqualTo(
                "organizerId",
                auth.currentUser?.uid
            )

            .get()

            .addOnSuccessListener { result ->

                for (document in result) {

                    firestore.collection("hackathons")

                        .document(document.id)

                        .delete()
                }

                setupMyHackathons()
            }
    }

    override fun onResume() {
        super.onResume()

        setupMyHackathons()
    }
}