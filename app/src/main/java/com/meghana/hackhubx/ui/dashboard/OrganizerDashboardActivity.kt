package com.meghana.hackhubx.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.meghana.hackhubx.databinding.ActivityOrganizerDashboardBinding

class OrganizerDashboardActivity
    : AppCompatActivity() {

    private lateinit var binding:
            ActivityOrganizerDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityOrganizerDashboardBinding
                .inflate(layoutInflater)

        setContentView(binding.root)

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

}