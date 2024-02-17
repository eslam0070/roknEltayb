package com.rokneltayb.presentation.cart

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
import com.rokneltayb.data.model.cart.coupon.Coupon
import com.rokneltayb.databinding.ItemCartBinding
import com.rokneltayb.databinding.ItemDiscountBinding


class DiscountAdapter(private val deleteItemClick: () -> Unit) :  ListAdapter<Coupon, DiscountAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemDiscountBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            ),deleteItemClick
        )

    }
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cart = getItem(position)
        holder.bind(cart)
    }


    class ViewHolder(private val binding: ItemDiscountBinding,private val deleteItemClick: () -> Unit):


        RecyclerView.ViewHolder(binding.root){
        val list: ArrayList<Coupon> = ArrayList()

        @RequiresApi(Build.VERSION_CODES.P)
        fun bind(coupon: Coupon) {
            binding.deleteDiscountImageView.setOnClickListener {
                deleteItemClick()
            }


            binding.nameDiscountTextView.text = coupon.name
        }




    }

    companion object DiffCallback : DiffUtil.ItemCallback<Coupon>() {
        override fun areItemsTheSame(oldItem: Coupon, newItem: Coupon): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Coupon, newItem: Coupon): Boolean {
            return oldItem.id == newItem.id
        }
    }


}