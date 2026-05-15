package com.meghana.hackhubx.ui.dashboard

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
    }
}