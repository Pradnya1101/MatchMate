package com.example.matchmate.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.service.autofill.UserData
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.matchmate.data.db.UserEntity
import com.example.matchmate.databinding.LayoutItemBinding
import androidx.core.graphics.toColorInt

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

            btnAccept.setOnClickListener {
                Toast.makeText(it.context, "Accepted", Toast.LENGTH_SHORT).show()
                onActionClick(user, "accepted")
            }

            btnDecline.setOnClickListener {
                Toast.makeText(it.context, "Declined", Toast.LENGTH_SHORT).show()
                onActionClick(user, "declined")
            }


            btnAccept.setBackgroundColor(Color.LTGRAY)
            btnDecline.setBackgroundColor(Color.LTGRAY)
            btnAccept.setTextColor(Color.BLACK)
            btnDecline.setTextColor(Color.BLACK)

            when (user.status) {
                "accepted" -> {
                    btnAccept.setBackgroundColor("#4CAF50".toColorInt())
                    btnAccept.setTextColor(Color.WHITE)
                }
                "declined" -> {
                    btnDecline.setBackgroundColor("#F44336".toColorInt())
                    btnDecline.setTextColor(Color.WHITE)
                }
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