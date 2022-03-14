package com.uchihan.smartb_card.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.uchihan.smartb_card.data.models.RoomUserModel
import com.uchihan.smartb_card.data.models.UserModel
import com.uchihan.smartb_card.data.models.userModelToRoomUserModel
import com.uchihan.smartb_card.databinding.AllCardsCustomRowBinding
import com.uchihan.smartb_card.databinding.CardCustomRowBinding


class AllCardsAdapter(
    private val context: Context,
    private val cardsList: List<RoomUserModel>,
    private var cardsCallbacks: AllCardsCallbacks
) :
    RecyclerView.Adapter<AllCardsAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: AllCardsCustomRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.layAction.setOnClickListener {
               cardsCallbacks.remove(cardsList[adapterPosition])
                (cardsList as ArrayList).removeAt(adapterPosition)
                notifyDataSetChanged()
                Toast.makeText(context,"Card Removed",Toast.LENGTH_LONG).show()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var binding = AllCardsCustomRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var card = cardsList[position]
        holder.binding.tvMobile.text = card.mobile
        holder.binding.tvAddress.text = card.address
        holder.binding.tvEmail.text = card.email
        holder.binding.tvJob.text = card.work
        holder.binding.tvName.text = card.name
    }

    override fun getItemCount(): Int {
        return cardsList.size
    }

    interface AllCardsCallbacks {
        fun remove(userModel: RoomUserModel)
    }
}