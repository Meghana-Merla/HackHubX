package com.meghana.hackhubx

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.meghana.hackhubx.adapter.HackathonAdapter
import com.meghana.hackhubx.databinding.ActivityMainBinding
import com.meghana.hackhubx.model.Hackathon
import com.meghana.hackhubx.model.User
import com.meghana.hackhubx.ui.dashboard.ApplicationsActivity
import android.view.View
import com.meghana.hackhubx.ui.dashboard.ProfileActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding:
            ActivityMainBinding

    private lateinit var auth:
            FirebaseAuth

    private lateinit var firestore:
            FirebaseFirestore

    private val appliedHackathons =
        mutableSetOf<String>()

    private val allHackathons =
        mutableListOf<Hackathon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityMainBinding.inflate(
                layoutInflater
            )

        setContentView(binding.root)

        auth =
            FirebaseAuth.getInstance()

        firestore =
            FirebaseFirestore.getInstance()

        loadUserData()

        setupHackathons()

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

        binding.etSearchHackathons
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

    private fun loadUserData() {

        val uid =
            auth.currentUser?.uid ?: return

        firestore.collection("users")

            .document(uid)

            .get()

            .addOnSuccessListener {

                val user =
                    it.toObject(
                        User::class.java
                    )

                binding.tvName.text =
                    "Welcome ${user?.name} 👋"

                binding.tvCollege.text =
                    user?.college
            }
    }

    private fun setupHackathons() {

        val hackathonList =
            mutableListOf<Hackathon>()

        val adapter =

            HackathonAdapter(

                hackathonList,

                appliedHackathons
            )

        binding.recyclerHackathons
            .layoutManager =

            LinearLayoutManager(this)

        binding.recyclerHackathons
            .adapter = adapter

        firestore.collection("hackathons")

            .addSnapshotListener { result, error ->

                if (
                    error != null ||
                    result == null
                ) {

                    return@addSnapshotListener
                }

                hackathonList.clear()

                allHackathons.clear()

                for (document in result) {

                    val hackathon =
                        document.toObject(
                            Hackathon::class.java
                        ).copy(

                            documentId =
                                document.id
                        )

                    hackathonList.add(
                        hackathon
                    )

                    allHackathons.add(
                        hackathon
                    )
                }

                adapter.notifyDataSetChanged()
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

        if (filteredList.isEmpty()) {

            binding.tvSearchEmptyState.visibility =
                View.VISIBLE

        } else {

            binding.tvSearchEmptyState.visibility =
                View.GONE
        }

        val adapter =

            HackathonAdapter(

                filteredList,

                appliedHackathons
            )

        binding.recyclerHackathons
            .adapter = adapter
    }
}