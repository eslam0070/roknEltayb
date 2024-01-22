package com.rokneltayb.presentation.more.order

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
import com.rokneltayb.databinding.ItemCartBinding
import com.rokneltayb.databinding.ItemMyorderBinding


class OrdersAdapter(private val itemClick: (OrdersData) -> Unit) :  ListAdapter<OrdersData, OrdersAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMyorderBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            ),itemClick
        )

    }
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = getItem(position)
        holder.bind(order)
    }


    class ViewHolder(private val binding: ItemMyorderBinding,private val itemClick: (OrdersData) -> Unit):
        RecyclerView.ViewHolder(binding.root){
        @RequiresApi(Build.VERSION_CODES.P)
        fun bind(order: OrdersData) {

            Glide.with(binding.root.context).load(order.image).into(binding.imageCartImageView)
            binding.nameOrderTextView.text = order.orderNum.toString()
            binding.dateOrderTextView.text = order.date

            binding.root.setOnClickListener {
                itemClick(order)
            }

            when(order.type){
                "pending"->{
                    binding.stateOrderTextView.setBackgroundResource(com.rokneltayb.R.drawable.custom_bg_statue_pending)
                }
                "preparing"->{
                    binding.stateOrderTextView.setBackgroundResource(com.rokneltayb.R.drawable.custom_bg_statue_preparing)
                }
                "on_way"->{
                    binding.stateOrderTextView.setBackgroundResource(com.rokneltayb.R.drawable.custom_bg_statue_onway)
                }
                "delivered"->{
                    binding.stateOrderTextView.setBackgroundResource(com.rokneltayb.R.drawable.custom_bg_statue_delivered)
                }
                "canceled"->{
                    binding.stateOrderTextView.setBackgroundResource(com.rokneltayb.R.drawable.custom_bg_statue_canceled)
                }
            }


        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<OrdersData>() {
        override fun areItemsTheSame(oldItem: OrdersData, newItem: OrdersData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: OrdersData, newItem: OrdersData): Boolean {
            return oldItem.id == newItem.id
        }
    }


}