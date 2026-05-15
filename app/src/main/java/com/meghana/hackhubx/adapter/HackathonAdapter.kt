package com.meghana.hackhubx.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meghana.hackhubx.databinding.ItemHackathonBinding
import com.meghana.hackhubx.model.Hackathon

class HackathonAdapter(

    private val hackathonList:
    List<Hackathon>,

    private val appliedHackathons:
    Set<String>,

    private val onApplyClick:
    ((Hackathon) -> Unit)?,

    private val onDeleteClick:
    ((Hackathon) -> Unit)?,

    private val onEditClick:
    ((Hackathon) -> Unit)?
):

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

        if (onApplyClick != null) {

            holder.binding.btnApply.visibility =
                android.view.View.VISIBLE

            if (
                appliedHackathons.contains(
                    hackathon.title
                )
            ) {

                holder.binding.btnApply.text =
                    "Applied"

                holder.binding.btnApply
                    .isEnabled = false

            } else {

                holder.binding.btnApply.text =
                    "Apply Now"

                holder.binding.btnApply
                    .isEnabled = true

                holder.binding.btnApply
                    .setOnClickListener {

                        onApplyClick.invoke(
                            hackathon
                        )
                    }
            }

        } else {

            holder.binding.btnApply.visibility =
                android.view.View.GONE
        }

        if (onEditClick != null) {

            holder.binding.btnEdit.visibility =
                android.view.View.VISIBLE

            holder.binding.btnEdit
                .setOnClickListener {

                    onEditClick.invoke(
                        hackathon
                    )
                }

        } else {

            holder.binding.btnEdit.visibility =
                android.view.View.GONE
        }

        if (onDeleteClick != null) {

            holder.binding.btnDelete.visibility =
                android.view.View.VISIBLE

            holder.binding.btnDelete
                .setOnClickListener {

                    onDeleteClick.invoke(
                        hackathon
                    )
                }

        } else {

            holder.binding.btnDelete.visibility =
                android.view.View.GONE
        }
    }

    override fun getItemCount(): Int {

        return hackathonList.size
    }
}