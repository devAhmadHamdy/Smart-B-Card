package com.uchihan.smartb_card.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.uchihan.smartb_card.R
import com.uchihan.smartb_card.common.Constants
import com.uchihan.smartb_card.data.models.RoomUserModel
import com.uchihan.smartb_card.data.models.UserModel
import com.uchihan.smartb_card.data.models.userModelToRoomUserModel
import com.uchihan.smartb_card.databinding.CardCustomRowBinding


class CardAdapter(
    private val context: Context,
    private val cardsList: ArrayList<UserModel>,
    val savedNearby: ArrayList<RoomUserModel>,
    private var cardsCallbacks: CardsCallbacks
) :
    RecyclerView.Adapter<CardAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: CardCustomRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.layAction.setOnClickListener {
                cardsCallbacks.saveToApp(userModelToRoomUserModel(cardsList[adapterPosition]))
                binding.layAction.visibility = View.GONE
                Toast.makeText(context, "Card Saved", Toast.LENGTH_LONG).show()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var binding = CardCustomRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var current = userModelToRoomUserModel(cardsList[position])
        if (savedNearby.contains(current)) {
            holder.binding.layAction.visibility = View.GONE
        }
        var card = cardsList[position]
        holder.binding.tvMobile.text = card.mobile
        holder.binding.tvAddress.text = card.address
        holder.binding.tvEmail.text = card.email
        holder.binding.tvJob.text = card.work
        holder.binding.tvName.text = card.name

        if (card.gender == Constants.GENDER_FEMALE)
            holder.binding.imgUser.setImageResource(R.drawable.woman)
    }

    override fun getItemCount(): Int {
        return cardsList.size
    }

    interface CardsCallbacks {
        fun saveToApp(userModel: RoomUserModel)
    }
}