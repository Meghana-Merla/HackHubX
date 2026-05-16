package com.meghana.hackhubx.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.meghana.hackhubx.databinding.ActivityProfileBinding
import com.meghana.hackhubx.model.User
import com.meghana.hackhubx.ui.auth.LoginActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding:
            ActivityProfileBinding

    private lateinit var auth:
            FirebaseAuth

    private lateinit var firestore:
            FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityProfileBinding.inflate(
                layoutInflater
            )

        setContentView(binding.root)

        auth =
            FirebaseAuth.getInstance()

        firestore =
            FirebaseFirestore.getInstance()

        loadProfile()

        binding.btnLogout
            .setOnClickListener {

                auth.signOut()

                startActivity(

                    Intent(
                        this,
                        LoginActivity::class.java
                    )
                )

                finishAffinity()
            }

        binding.btnEditProfile
            .setOnClickListener {

                startActivity(

                    Intent(
                        this,
                        EditProfileActivity::class.java
                    )
                )
            }
    }

    private fun loadProfile() {

        val uid =
            auth.currentUser?.uid ?: return

        firestore.collection("users")

            .document(uid)

            .get()

            .addOnSuccessListener {

                val user =
                    it.toObject(User::class.java)

                binding.tvName.text =
                    user?.name

                binding.tvCollege.text =
                    user?.college

                binding.tvEmail.text =
                    user?.email

                binding.tvBio.text =
                    user?.bio

                binding.tvSkills.text =
                    user?.skills

                binding.tvBranch.text =
                    "Branch: ${user?.branch}"

                binding.tvYear.text =
                    "Year: ${user?.year}"

                binding.tvGithub.text =
                    user?.github

                binding.tvLinkedin.text =
                    user?.linkedin

                Glide.with(this)

                    .load(
                        user?.profileImageUrl
                    )

                    .into(binding.imgProfile)
            }
    }

    override fun onResume() {
        super.onResume()

        loadProfile()
    }
}