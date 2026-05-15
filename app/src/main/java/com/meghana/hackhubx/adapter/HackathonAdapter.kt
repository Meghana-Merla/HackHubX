package com.meghana.hackhubx.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.meghana.hackhubx.databinding.ItemHackathonBinding
import com.meghana.hackhubx.model.Hackathon
import com.meghana.hackhubx.ui.dashboard.HackathonDetailsActivity

class HackathonAdapter(

    private val hackathonList:
    List<Hackathon>,

    private val appliedHackathons:
    Set<String>

) : RecyclerView.Adapter<
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

                LayoutInflater.from(
                    parent.context
                ),

                parent,

                false
            )

        return HackathonViewHolder(
            binding
        )
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

        Glide.with(holder.itemView.context)

            .load(hackathon.imageUrl)

            .into(holder.binding.imgHackathon)

        holder.itemView.setOnClickListener {

            val intent =

                Intent(

                    holder.itemView.context,

                    HackathonDetailsActivity::class.java
                )

            intent.putExtra(
                "title",
                hackathon.title
            )

            intent.putExtra(
                "prize",
                hackathon.prize
            )

            intent.putExtra(
                "teamSize",
                hackathon.teamSize
            )

            intent.putExtra(
                "deadline",
                hackathon.deadline
            )

            intent.putExtra(
                "description",
                hackathon.description
            )

            intent.putExtra(
                "imageUrl",
                hackathon.imageUrl
            )

            intent.putExtra(
                "organizerId",
                hackathon.organizerId
            )

            intent.putExtra(
                "documentId",
                hackathon.documentId
            )

            holder.itemView.context
                .startActivity(intent)
        }
    }

    override fun getItemCount(): Int {

        return hackathonList.size
    }
}