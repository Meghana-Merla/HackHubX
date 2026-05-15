package com.meghana.hackhubx.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.meghana.hackhubx.databinding.ActivityHackathonDetailsBinding
import com.meghana.hackhubx.model.Application

class HackathonDetailsActivity
    : AppCompatActivity() {

    private lateinit var binding:
            ActivityHackathonDetailsBinding

    private lateinit var firestore:
            FirebaseFirestore

    private lateinit var auth:
            FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityHackathonDetailsBinding
                .inflate(layoutInflater)

        setContentView(binding.root)

        firestore =
            FirebaseFirestore.getInstance()

        auth =
            FirebaseAuth.getInstance()

        val currentUserId =
            auth.currentUser?.uid

        val title =
            intent.getStringExtra(
                "title"
            ) ?: ""

        val organizerId =
            intent.getStringExtra(
                "organizerId"
            )

        val documentId =
            intent.getStringExtra(
                "documentId"
            ) ?: ""

        binding.tvTitle.text =
            title

        binding.tvPrize.text =
            "Prize: ${intent.getStringExtra("prize")}"

        binding.tvTeamSize.text =
            "Team Size: ${intent.getStringExtra("teamSize")}"

        binding.tvDeadline.text =
            "Deadline: ${intent.getStringExtra("deadline")}"

        binding.tvDescription.text =
            intent.getStringExtra(
                "description"
            )

        Glide.with(this)

            .load(
                intent.getStringExtra(
                    "imageUrl"
                )
            )

            .into(binding.imgHackathon)

        if (
            currentUserId ==
            organizerId
        ) {

            binding.btnApply.visibility =
                View.GONE

            binding.btnEdit.visibility =
                View.VISIBLE

            binding.btnDelete.visibility =
                View.VISIBLE

            binding.btnViewApplicants.visibility =
                View.VISIBLE

        } else {

            checkIfAlreadyApplied(
                title
            )
        }

        binding.btnApply
            .setOnClickListener {

                applyToHackathon(
                    title
                )
            }

        binding.btnEdit
            .setOnClickListener {

                val editIntent =

                    Intent(
                        this,
                        AddHackathonActivity::class.java
                    )

                editIntent.putExtra(
                    "title",
                    title
                )

                editIntent.putExtra(
                    "prize",
                    intent.getStringExtra(
                        "prize"
                    )
                )

                editIntent.putExtra(
                    "teamSize",
                    intent.getStringExtra(
                        "teamSize"
                    )
                )

                editIntent.putExtra(
                    "deadline",
                    intent.getStringExtra(
                        "deadline"
                    )
                )

                editIntent.putExtra(
                    "description",
                    intent.getStringExtra(
                        "description"
                    )
                )

                editIntent.putExtra(
                    "imageUrl",
                    intent.getStringExtra(
                        "imageUrl"
                    )
                )

                editIntent.putExtra(
                    "documentId",
                    documentId
                )

                startActivity(editIntent)
            }

        binding.btnViewApplicants
            .setOnClickListener {

                val intent =

                    Intent(
                        this,
                        ApplicantsActivity::class.java
                    )

                intent.putExtra(
                    "hackathonTitle",
                    title
                )

                startActivity(intent)
            }

        binding.btnDelete
            .setOnClickListener {

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
                            documentId
                        )
                    }

                    .setNegativeButton(
                        "Cancel",
                        null
                    )

                    .show()
            }
    }

    private fun checkIfAlreadyApplied(
        title: String
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
                title
            )

            .get()

            .addOnSuccessListener { result ->

                if (!result.isEmpty) {

                    binding.btnApply.text =
                        "Already Applied"

                    binding.btnApply.isEnabled =
                        false
                }
            }
    }

    private fun applyToHackathon(
        title: String
    ) {

        val uid =
            auth.currentUser?.uid ?: return

        binding.btnApply.isEnabled =
            false

        binding.btnApply.text =
            "Registering..."

        val application =
            Application(

                userId = uid,

                hackathonTitle =
                    title,

                timestamp =
                    System.currentTimeMillis()
            )

        firestore.collection("applications")

            .add(application)

            .addOnSuccessListener {

                binding.btnApply.text =
                    "Already Applied"

                Toast.makeText(
                    this,
                    "Registered Successfully",
                    Toast.LENGTH_SHORT
                ).show()
            }

            .addOnFailureListener {

                binding.btnApply.isEnabled =
                    true

                binding.btnApply.text =
                    "Register Now"

                Toast.makeText(
                    this,
                    it.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun deleteHackathon(
        documentId: String
    ) {

        firestore.collection("hackathons")

            .document(documentId)

            .delete()

            .addOnSuccessListener {

                Toast.makeText(
                    this,
                    "Hackathon Deleted",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }
    }
}