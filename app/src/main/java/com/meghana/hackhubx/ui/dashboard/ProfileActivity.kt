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
import android.net.Uri

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

                    if (
                        user?.bio.isNullOrEmpty()
                    ) {

                        "No bio added yet"

                    } else {

                        user?.bio
                    }

                binding.tvSkills.text =

                    if (
                        user?.skills.isNullOrEmpty()
                    ) {

                        "No skills added"

                    } else {

                        user?.skills
                    }

                binding.tvBranch.text =
                    "Branch: ${user?.branch}"

                binding.tvYear.text =
                    "Year: ${user?.year}"

                binding.tvGithub.text =

                    if (
                        user?.github.isNullOrEmpty()
                    ) {

                        "No GitHub link added"

                    } else {

                        user?.github
                    }

                binding.tvLinkedin.text =

                    if (
                        user?.linkedin.isNullOrEmpty()
                    ) {

                        "No LinkedIn link added"

                    } else {

                        user?.linkedin
                    }

                binding.tvGithub.setOnClickListener {

                    val githubUrl =
                        user?.github

                    if (!githubUrl.isNullOrEmpty()) {

                        startActivity(

                            Intent(

                                Intent.ACTION_VIEW,

                                Uri.parse(githubUrl)
                            )
                        )
                    }
                }

                binding.tvLinkedin.setOnClickListener {

                    val linkedinUrl =
                        user?.linkedin

                    if (!linkedinUrl.isNullOrEmpty()) {

                        startActivity(

                            Intent(

                                Intent.ACTION_VIEW,

                                Uri.parse(linkedinUrl)
                            )
                        )
                    }
                }

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