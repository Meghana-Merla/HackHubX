package com.meghana.hackhubx.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meghana.hackhubx.databinding.ItemApplicantBinding
import com.meghana.hackhubx.model.Applicant

class ApplicantAdapter(

    private val applicantList:
    List<Applicant>

) : RecyclerView.Adapter<
        ApplicantAdapter.ApplicantViewHolder>() {

    inner class ApplicantViewHolder(
        val binding: ItemApplicantBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ApplicantViewHolder {

        val binding =
            ItemApplicantBinding.inflate(

                LayoutInflater.from(
                    parent.context
                ),

                parent,

                false
            )

        return ApplicantViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(
        holder: ApplicantViewHolder,
        position: Int
    ) {

        val applicant =
            applicantList[position]

        holder.binding.tvApplicantName.text =
            applicant.name

        holder.binding.tvApplicantCollege.text =
            applicant.college

        holder.binding.tvApplicantEmail.text =
            applicant.email
    }

    override fun getItemCount(): Int {

        return applicantList.size
    }
}