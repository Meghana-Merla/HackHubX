package com.meghana.hackhubx.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.meghana.hackhubx.adapter.ApplicationAdapter
import com.meghana.hackhubx.databinding.ActivityApplicationsBinding
import com.meghana.hackhubx.model.Application

class ApplicationsActivity : AppCompatActivity() {

    private lateinit var binding:
            ActivityApplicationsBinding

    private lateinit var auth: FirebaseAuth

    private lateinit var firestore:
            FirebaseFirestore

    private lateinit var adapter:
            ApplicationAdapter

    private val applicationList =
        mutableListOf<Application>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityApplicationsBinding.inflate(
                layoutInflater
            )

        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        firestore =
            FirebaseFirestore.getInstance()

        setupRecyclerView()

        loadApplications()
    }

    private fun setupRecyclerView() {

        adapter =
            ApplicationAdapter(applicationList)

        binding.recyclerApplications.layoutManager =
            LinearLayoutManager(this)

        binding.recyclerApplications.adapter =
            adapter
    }

    private fun loadApplications() {

        val uid =
            auth.currentUser?.uid ?: return

        firestore.collection("applications")
            .whereEqualTo("userId", uid)
            .get()

            .addOnSuccessListener { result ->

                applicationList.clear()

                for (document in result) {

                    val application =
                        document.toObject(
                            Application::class.java
                        )

                    applicationList.add(application)
                }

                adapter.notifyDataSetChanged()

                if (applicationList.isEmpty()) {

                    binding.tvEmptyApplications.visibility =
                        View.VISIBLE

                } else {

                    binding.tvEmptyApplications.visibility =
                        View.GONE
                }
            }
    }
}