package com.meghana.hackhubx.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.meghana.hackhubx.adapter.HackathonAdapter
import com.meghana.hackhubx.databinding.ActivityOrganizerDashboardBinding
import com.meghana.hackhubx.model.Hackathon

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

                object : TextWatcher {

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
                        s: Editable?
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

                emptySet()
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

                emptySet()
            )

        binding.recyclerMyHackathons
            .adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        setupMyHackathons()
    }
}