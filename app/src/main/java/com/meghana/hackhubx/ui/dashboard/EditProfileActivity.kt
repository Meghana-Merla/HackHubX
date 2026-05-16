package com.meghana.hackhubx.ui.dashboard

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.meghana.hackhubx.databinding.ActivityEditProfileBinding
import com.bumptech.glide.Glide

class EditProfileActivity
    : AppCompatActivity() {

    private lateinit var binding:
            ActivityEditProfileBinding

    private lateinit var firestore:
            FirebaseFirestore

    private lateinit var auth:
            FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityEditProfileBinding.inflate(
                layoutInflater
            )

        setContentView(binding.root)

        firestore =
            FirebaseFirestore.getInstance()

        auth =
            FirebaseAuth.getInstance()

        loadProfileData()

        val years = arrayOf(

            "1st Year",

            "2nd Year",

            "3rd Year",

            "4th Year",
            "Graduated"
        )

        val adapter =

            android.widget.ArrayAdapter(

                this,

                android.R.layout
                    .simple_dropdown_item_1line,

                years
            )

        binding.etYear.setAdapter(adapter)

        binding.btnSaveProfile
            .setOnClickListener {

                saveProfile()
            }
    }

    private fun loadProfileData() {

        val uid =
            auth.currentUser?.uid ?: return

        firestore.collection("users")

            .document(uid)

            .get()

            .addOnSuccessListener {

                binding.etName.setText(

                    it.getString(
                        "name"
                    )
                )

                binding.etCollege.setText(

                    it.getString(
                        "college"
                    )
                )

                binding.etEmail.setText(

                    it.getString(
                        "email"
                    )
                )

                val imageUrl =

                    it.getString(
                        "profileImageUrl"
                    )

                binding.etProfileImage.setText(
                    imageUrl
                )

                binding.etBio.setText(

                    it.getString(
                        "bio"
                    )
                )

                binding.etSkills.setText(

                    it.getString(
                        "skills"
                    )
                )

                binding.etBranch.setText(

                    it.getString(
                        "branch"
                    )
                )

                binding.etYear.setText(

                    it.getString(
                        "year"
                    )
                )

                binding.etGithub.setText(

                    it.getString(
                        "github"
                    )
                )

                binding.etLinkedin.setText(

                    it.getString(
                        "linkedin"
                    )
                )

                Glide.with(this)

                    .load(imageUrl)

                    .into(binding.imgProfilePreview)
            }
    }

    private fun saveProfile() {

        val uid =
            auth.currentUser?.uid ?: return

        val name =
            binding.etName.text
                .toString()
                .trim()

        val college =
            binding.etCollege.text
                .toString()
                .trim()

        val bio =
            binding.etBio.text
                .toString()
                .trim()

        val branch =
            binding.etBranch.text
                .toString()
                .trim()

        val year =
            binding.etYear.text
                .toString()
                .trim()

        if (bio.isEmpty()) {

            Toast.makeText(
                this,
                "Bio is required",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        if (branch.isEmpty()) {

            Toast.makeText(
                this,
                "Branch is required",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        if (year.isEmpty()) {

            Toast.makeText(
                this,
                "Year is required",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val updates =
            hashMapOf<String, Any>(

                "name" to name,

                "college" to college,

                "profileImageUrl" to
                        binding.etProfileImage.text
                            .toString()
                            .trim(),

                "bio" to bio,

                "skills" to
                        binding.etSkills.text
                            .toString()
                            .trim(),

                "branch" to branch,

                "year" to year,

                "github" to
                        binding.etGithub.text
                            .toString()
                            .trim(),

                "linkedin" to
                        binding.etLinkedin.text
                            .toString()
                            .trim()
            )

        binding.btnSaveProfile.isEnabled =
            false

        binding.btnSaveProfile.text =
            "Saving..."

        firestore.collection("users")

            .document(uid)

            .update(updates)

            .addOnSuccessListener {

                binding.btnSaveProfile.isEnabled =
                    true

                binding.btnSaveProfile.text =
                    "Save Profile"

                Toast.makeText(
                    this,
                    "Profile Updated",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }

            .addOnFailureListener {

                binding.btnSaveProfile.isEnabled =
                    true

                binding.btnSaveProfile.text =
                    "Save Profile"

                Toast.makeText(
                    this,
                    it.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}