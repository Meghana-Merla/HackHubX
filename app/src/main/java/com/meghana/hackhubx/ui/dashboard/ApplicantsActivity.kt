package com.meghana.hackhubx.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.meghana.hackhubx.adapter.ApplicantAdapter
import com.meghana.hackhubx.databinding.ActivityApplicantsBinding
import com.meghana.hackhubx.model.Applicant

class ApplicantsActivity
    : AppCompatActivity() {

    private lateinit var binding:
            ActivityApplicantsBinding

    private lateinit var firestore:
            FirebaseFirestore

    private lateinit var adapter:
            ApplicantAdapter

    private val applicantList =
        mutableListOf<Applicant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityApplicantsBinding.inflate(
                layoutInflater
            )

        setContentView(binding.root)

        firestore =
            FirebaseFirestore.getInstance()

        setupRecyclerView()

        loadApplicants()
    }

    private fun setupRecyclerView() {

        adapter =
            ApplicantAdapter(
                applicantList
            )

        binding.recyclerApplicants.layoutManager =
            LinearLayoutManager(this)

        binding.recyclerApplicants.adapter =
            adapter
    }

    private fun loadApplicants() {

        val hackathonTitle =
            intent.getStringExtra(
                "hackathonTitle"
            ) ?: return

        firestore.collection("applications")

            .whereEqualTo(
                "hackathonTitle",
                hackathonTitle
            )

            .get()

            .addOnSuccessListener { result ->

                applicantList.clear()

                if (result.isEmpty) {

                    binding.tvEmptyApplicants.visibility =
                        View.VISIBLE
                }

                for (document in result) {

                    val userId =
                        document.getString(
                            "userId"
                        ) ?: continue

                    firestore.collection("users")

                        .document(userId)

                        .get()

                        .addOnSuccessListener { userDoc ->

                            val name =
                                userDoc.getString(
                                    "name"
                                ) ?: ""

                            val college =
                                userDoc.getString(
                                    "college"
                                ) ?: ""

                            val email =
                                userDoc.getString(
                                    "email"
                                ) ?: ""

                            applicantList.add(

                                Applicant(
                                    name,
                                    college,
                                    email
                                )
                            )

                            adapter.notifyDataSetChanged()

                            binding.tvEmptyApplicants.visibility =
                                View.GONE
                        }
                }
            }
    }
}