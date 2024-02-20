package com.rokneltayb.presentation.cart.checkout

import android.R
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rokneltayb.data.model.cart.Cart
import com.rokneltayb.data.model.cart.CountModel
import com.rokneltayb.databinding.ItemCartBinding
import com.rokneltayb.databinding.ItemCartPlaceorderBinding


class CheckOutAdapter() :  ListAdapter<Cart, CheckOutAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCartPlaceorderBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )

    }
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cart = getItem(position)
        holder.bind(cart)
    }


    class ViewHolder(private val binding: ItemCartPlaceorderBinding):


        RecyclerView.ViewHolder(binding.root){
        val list: ArrayList<CountModel> = ArrayList()

        @RequiresApi(Build.VERSION_CODES.P)
        fun bind(cart: Cart) {

            Glide.with(binding.root.context).load(cart.productImage).into(binding.imageCartImageView)
            binding.nameCartTextView.text = cart.productTitle
            binding.countItemTextView.text = "x "+cart.count.toString()
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Cart>() {
        override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            return oldItem.id == newItem.id
        }
    }


}