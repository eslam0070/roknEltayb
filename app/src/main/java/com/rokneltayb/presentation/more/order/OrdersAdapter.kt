package com.rokneltayb.presentation.more.order

import android.R
import android.graphics.Color
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
            binding.nameOrderTextView.text = binding.root.context.getString(com.rokneltayb.R.string.order_id2)+order.orderNum.toString()
            binding.dateOrderTextView.text = order.date

            binding.root.setOnClickListener {
                itemClick(order)
            }

            when(order.type){
                "pending"->{
                    binding.stateOrderTextView.setBackgroundResource(com.rokneltayb.R.drawable.custom_bg_statue_pending)
                    binding.stateOrderTextView.text = binding.root.context.getString(com.rokneltayb.R.string.pending_confirmation)
                    binding.stateOrderTextView.setTextColor(Color.BLACK)
                }
                "preparing"->{
                    binding.stateOrderTextView.setBackgroundResource(com.rokneltayb.R.drawable.custom_bg_statue_preparing)
                    binding.stateOrderTextView.text = binding.root.context.getString(com.rokneltayb.R.string.waiting_to_be_shipped)
                    binding.stateOrderTextView.setTextColor(Color.BLACK)
                }
                "on_way"->{
                    binding.stateOrderTextView.setBackgroundResource(com.rokneltayb.R.drawable.custom_bg_statue_onway)
                    binding.stateOrderTextView.text = binding.root.context.getString(com.rokneltayb.R.string.on_way)
                    binding.stateOrderTextView.setTextColor(Color.BLACK)
                }
                "delivered"->{
                    binding.stateOrderTextView.setBackgroundResource(com.rokneltayb.R.drawable.custom_bg_statue_delivered)
                    binding.stateOrderTextView.text = binding.root.context.getString(com.rokneltayb.R.string.delivered)
                    binding.stateOrderTextView.setTextColor(Color.WHITE)
                }
                "canceled"->{
                    binding.stateOrderTextView.setBackgroundResource(com.rokneltayb.R.drawable.custom_bg_statue_canceled)
                    binding.stateOrderTextView.text = binding.root.context.getString(com.rokneltayb.R.string.cancel)
                    binding.stateOrderTextView.setTextColor(Color.WHITE)
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