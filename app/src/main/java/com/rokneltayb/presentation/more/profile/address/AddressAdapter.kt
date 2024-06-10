package com.rokneltayb.presentation.more.profile.address

import android.content.Context
import android.graphics.Paint
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rokneltayb.R
import com.rokneltayb.data.model.address.AddressData
import com.rokneltayb.data.model.home.home.PopularProduct
import com.rokneltayb.databinding.ItemAddressBinding
import com.rokneltayb.databinding.ItemHomePupularProductsBinding

class AddressAdapter(
    private val itemClick: (AddressData) -> Unit
) :  ListAdapter<AddressData, AddressAdapter.ViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemAddressBinding.inflate(LayoutInflater.from(parent.context)), itemClick)
    }



    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val address = getItem(position)
        holder.bind(address)
    }

    class ViewHolder(private val binding: ItemAddressBinding, private val itemClick: (AddressData) -> Unit):RecyclerView.ViewHolder(binding.root){
        @RequiresApi(Build.VERSION_CODES.P)
        fun bind(address: AddressData) {
            binding.addressTextView.text = address.name
            binding.root.setOnClickListener {
                itemClick(address)
            }

        }

    }
    companion object DiffCallback : DiffUtil.ItemCallback<AddressData>() {
        override fun areItemsTheSame(oldItem: AddressData, newItem: AddressData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: AddressData, newItem: AddressData): Boolean {
            return oldItem.id == newItem.id
        }
    }
}