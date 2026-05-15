package com.meghana.hackhubx.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meghana.hackhubx.databinding.ItemHackathonBinding
import com.meghana.hackhubx.model.Hackathon

class HackathonAdapter(
    private val hackathonList:
    List<Hackathon>
) :

    RecyclerView.Adapter<
            HackathonAdapter.HackathonViewHolder>() {

    inner class HackathonViewHolder(
        val binding: ItemHackathonBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HackathonViewHolder {

        val binding =
            ItemHackathonBinding.inflate(

                LayoutInflater.from(parent.context),

                parent,

                false
            )

        return HackathonViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: HackathonViewHolder,
        position: Int
    ) {

        val hackathon =
            hackathonList[position]

        holder.binding.tvHackathonTitle.text =
            hackathon.title

        holder.binding.tvPrize.text =
            "Prize: ${hackathon.prize}"

        holder.binding.tvTeamSize.text =
            "Team Size: ${hackathon.teamSize}"

        holder.binding.tvDeadline.text =
            "Deadline: ${hackathon.deadline}"
    }

    override fun getItemCount(): Int {

        return hackathonList.size
    }
}