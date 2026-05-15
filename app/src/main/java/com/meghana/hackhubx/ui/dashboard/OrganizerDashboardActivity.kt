package com.meghana.hackhubx.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.meghana.hackhubx.adapter.HackathonAdapter
import com.meghana.hackhubx.databinding.ActivityOrganizerDashboardBinding
import com.meghana.hackhubx.model.Hackathon
import android.text.Editable
import android.text.TextWatcher

class OrganizerDashboardActivity
    : AppCompatActivity() {

    private lateinit var binding:
            ActivityOrganizerDashboardBinding

    private lateinit var firestore:
            FirebaseFirestore

    private lateinit var auth:
            FirebaseAuth

    private val allHackathons =
        mutableListOf<Hackathon>()

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

        binding.btnProfile
            .setOnClickListener {

                startActivity(

                    Intent(
                        this,
                        ProfileActivity::class.java
                    )
                )
            }

        binding.etSearchMyHackathons
            .addTextChangedListener(

                object : android.text.TextWatcher {

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {

                        filterHackathons(
                            s.toString()
                        )
                    }

                    override fun afterTextChanged(
                        s: android.text.Editable?
                    ) {
                    }
                }
            )
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

            .addSnapshotListener { result, error ->

                if (
                    error != null ||
                    result == null
                ) {

                    return@addSnapshotListener
                }

                hackathonList.clear()

                allHackathons.clear()

                if (result.isEmpty) {

                    binding.tvEmptyState.visibility =
                        View.VISIBLE

                } else {

                    binding.tvEmptyState.visibility =
                        View.GONE
                }

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

                        .addSnapshotListener { applications, _ ->

                            val count =
                                applications?.size() ?: 0

                            val updatedHackathon =

                                hackathon.copy(

                                    applicantCount =
                                        count
                                )

                            hackathonList.removeAll {

                                it.documentId ==
                                        updatedHackathon.documentId
                            }

                            allHackathons.removeAll {

                                it.documentId ==
                                        updatedHackathon.documentId
                            }

                            hackathonList.add(
                                updatedHackathon
                            )

                            allHackathons.add(
                                updatedHackathon
                            )

                            adapter.notifyDataSetChanged()

                            binding.tvEmptyState.visibility =
                                View.GONE
                        }
                }
            }
    }
    private fun filterHackathons(
        query: String
    ) {

        val filteredList =

            allHackathons.filter {

                it.title.contains(
                    query,
                    ignoreCase = true
                )
            }

        val adapter =

            HackathonAdapter(

                filteredList,

                emptySet(),

                null,

                { hackathon ->

                    androidx.appcompat.app
                        .AlertDialog
                        .Builder(this)

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

        binding.recyclerMyHackathons.adapter =
            adapter
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