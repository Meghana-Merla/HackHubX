package com.meghana.hackhubx.ui.dashboard

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.meghana.hackhubx.databinding.ActivityAddHackathonBinding
import com.meghana.hackhubx.model.Hackathon
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddHackathonActivity
    : AppCompatActivity() {

    private lateinit var binding:
            ActivityAddHackathonBinding

    private lateinit var firestore:
            FirebaseFirestore

    private var isEditMode =
        false

    private var oldTitle = ""

    private var documentId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityAddHackathonBinding
                .inflate(layoutInflater)

        setContentView(binding.root)

        firestore =
            FirebaseFirestore.getInstance()

        checkEditMode()

        binding.btnCreateHackathon
            .setOnClickListener {

                createHackathon()
            }

        binding.etDeadline
            .setOnClickListener {

                showDatePicker()
            }
    }

    private fun checkEditMode() {

        val title =
            intent.getStringExtra("title")

        if (title != null) {

            isEditMode = true

            oldTitle = title

            documentId =
                intent.getStringExtra(
                    "documentId"
                ) ?: ""

            binding.etTitle.setText(title)

            val prize =
                intent.getStringExtra(
                    "prize"
                ) ?: ""

            val prizeParts =
                prize.split(" ")

            if (prizeParts.size >= 2) {

                binding.etPrize.setText(
                    prizeParts.last()
                )
            }

            val teamSize =
                intent.getStringExtra(
                    "teamSize"
                ) ?: ""

            val teamParts =
                teamSize.split("-")

            if (teamParts.size == 2) {

                binding.etMinTeamSize
                    .setText(teamParts[0])

                binding.etMaxTeamSize
                    .setText(teamParts[1])
            }

            binding.etDeadline.setText(

                intent.getStringExtra(
                    "deadline"
                )
            )

            binding.btnCreateHackathon.text =
                "Update Hackathon"
        }
    }

    private fun createHackathon() {

        val title =
            binding.etTitle.text
                .toString()
                .trim()

        val prize =
            binding.etPrize.text
                .toString()
                .trim()

        val currency =
            binding.spinnerCurrency
                .selectedItem
                .toString()

        val minTeamSize =
            binding.etMinTeamSize.text
                .toString()
                .trim()

        val maxTeamSize =
            binding.etMaxTeamSize.text
                .toString()
                .trim()

        val deadline =
            binding.etDeadline.text
                .toString()
                .trim()

        when {

            title.isEmpty() -> {

                showToast(
                    "Enter title"
                )
            }

            prize.isEmpty() -> {

                showToast(
                    "Enter prize"
                )
            }

            minTeamSize.isEmpty() -> {

                showToast(
                    "Enter minimum team size"
                )
            }

            maxTeamSize.isEmpty() -> {

                showToast(
                    "Enter maximum team size"
                )
            }

            minTeamSize.toInt() >
                    maxTeamSize.toInt() -> {

                showToast(
                    "Minimum team size cannot be greater than maximum"
                )
            }

            deadline.isEmpty() -> {

                showToast(
                    "Enter deadline"
                )
            }

            else -> {

                val organizerId =
                    com.google.firebase.auth
                        .FirebaseAuth
                        .getInstance()
                        .currentUser
                        ?.uid ?: ""

                val hackathon = Hackathon(

                    title = title,

                    prize =
                        "$currency $prize",

                    teamSize =
                        "$minTeamSize-$maxTeamSize",

                    deadline = deadline,

                    organizerId = organizerId,

                    documentId = documentId
                )

                if (isEditMode) {

                    firestore.collection("hackathons")

                        .document(documentId)

                        .set(hackathon)

                        .addOnSuccessListener {

                            showToast(
                                "Hackathon Updated"
                            )

                            finish()
                        }

                        .addOnFailureListener {

                            showToast(
                                it.message.toString()
                            )
                        }

                } else {

                    firestore.collection("hackathons")

                        .add(hackathon)

                        .addOnSuccessListener {

                            showToast(
                                "Hackathon Created"
                            )

                            finish()
                        }

                        .addOnFailureListener {

                            showToast(
                                it.message.toString()
                            )
                        }
                }
            }
        }
    }

    private fun showDatePicker() {

        val calendar =
            Calendar.getInstance()

        val datePickerDialog =
            DatePickerDialog(

                this,

                { _, year, month, dayOfMonth ->

                    val selectedCalendar =
                        Calendar.getInstance()

                    selectedCalendar.set(
                        year,
                        month,
                        dayOfMonth
                    )

                    showTimePicker(
                        selectedCalendar
                    )
                },

                calendar.get(Calendar.YEAR),

                calendar.get(Calendar.MONTH),

                calendar.get(Calendar.DAY_OF_MONTH)
            )

        calendar.add(
            Calendar.DAY_OF_MONTH,
            1
        )

        datePickerDialog.datePicker.minDate =
            calendar.timeInMillis

        datePickerDialog.show()
    }

    private fun showTimePicker(
        calendar: Calendar
    ) {

        val timePickerDialog =
            TimePickerDialog(

                this,

                { _, hourOfDay, minute ->

                    calendar.set(
                        Calendar.HOUR_OF_DAY,
                        hourOfDay
                    )

                    calendar.set(
                        Calendar.MINUTE,
                        minute
                    )

                    val format =
                        SimpleDateFormat(

                            "dd MMM yyyy, hh:mm a",

                            Locale.getDefault()
                        )

                    binding.etDeadline.setText(

                        format.format(
                            calendar.time
                        )
                    )
                },

                12,

                0,

                false
            )

        timePickerDialog.show()
    }

    private fun showToast(
        message: String
    ) {

        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}