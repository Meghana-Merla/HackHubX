package com.meghana.hackhubx.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meghana.hackhubx.databinding.ItemApplicationBinding
import com.meghana.hackhubx.model.Application

class ApplicationAdapter(

    private val applicationList:
    List<Application>

) : RecyclerView.Adapter<
        ApplicationAdapter.ApplicationViewHolder>() {

    inner class ApplicationViewHolder(

        val binding: ItemApplicationBinding

    ) : RecyclerView.ViewHolder(
        binding.root
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ApplicationViewHolder {

        val binding =
            ItemApplicationBinding.inflate(

                LayoutInflater.from(parent.context),

                parent,

                false
            )

        return ApplicationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ApplicationViewHolder,
        position: Int
    ) {

        val application =
            applicationList[position]

        holder.binding.tvApplicationTitle.text =
            application.hackathonTitle

        holder.binding.tvStatus.text =
            "Applied Successfully ✅"
    }

    override fun getItemCount(): Int {

        return applicationList.size
    }
}