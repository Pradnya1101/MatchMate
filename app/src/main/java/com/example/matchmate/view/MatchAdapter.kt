package com.example.matchmate.view

import android.annotation.SuppressLint
import android.service.autofill.UserData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.matchmate.data.db.UserEntity
import com.example.matchmate.databinding.LayoutItemBinding

class MatchAdapter( private var userList: List<UserEntity>,
                    private val onActionClick: (UserEntity, String) -> Unit) :
    RecyclerView.Adapter<MatchAdapter.MatchViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MatchViewHolder {
        val binding = LayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MatchViewHolder,
        position: Int
    ) {
       val user = userList[position]
        with(holder.binding) {
            tvName.text = "${user.firstName} ${user.lastName}"
            tvLocation.text = "${user.city}, ${user.country}"

            Glide.with(imgProfile.context)
                .load(user.imageUrl)
                .into(imgProfile)

            btnAccept.setOnClickListener { onActionClick(user, "accepted") }
            btnDecline.setOnClickListener { onActionClick(user, "declined") }

            // Update UI based on status
            when (user.status) {
                "accepted" -> tvStatus.text = "Member Accepted"
                "declined" -> tvStatus.text = "Member Declined"
                else -> tvStatus.text = ""
            }
        }
    }


    override fun getItemCount(): Int {
        return userList.size
    }
    fun updateList(newList: List<UserEntity>) {
        userList = newList
        notifyDataSetChanged()
    }
    inner class MatchViewHolder(val binding: LayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}