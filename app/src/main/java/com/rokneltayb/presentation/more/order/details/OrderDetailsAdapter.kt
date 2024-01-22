package com.rokneltayb.presentation.more.order.details

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
import com.rokneltayb.data.model.orders.OrdersData
import com.rokneltayb.data.model.orders.details.OrderDetail
import com.rokneltayb.data.model.orders.details.OrderDetailsData
import com.rokneltayb.databinding.ItemCartBinding
import com.rokneltayb.databinding.ItemMyorderBinding
import com.rokneltayb.databinding.ItemMyorderDetailsBinding


class OrderDetailsAdapter() :  ListAdapter<OrderDetail, OrderDetailsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMyorderDetailsBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )

    }
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = getItem(position)
        holder.bind(order)
    }


    class ViewHolder(private val binding: ItemMyorderDetailsBinding):
        RecyclerView.ViewHolder(binding.root){
        @RequiresApi(Build.VERSION_CODES.P)
        fun bind(order: OrderDetail) {

            Glide.with(binding.root.context).load(order.item!!.image).into(binding.imageCartImageView)
            binding.nameOrderTextView.text = order.title.toString()
            binding.qTYTextView.text = binding.root.context.getString(com.rokneltayb.R.string.qty) + order.count
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<OrderDetail>() {
        override fun areItemsTheSame(oldItem: OrderDetail, newItem: OrderDetail): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: OrderDetail, newItem: OrderDetail): Boolean {
            return oldItem.id == newItem.id
        }
    }


}