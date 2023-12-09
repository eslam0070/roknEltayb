package com.rokneltayb.presentation.home.adapters

import android.graphics.Paint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rokneltayb.data.model.home.home.Category
import com.rokneltayb.data.model.home.home.PopularProduct
import com.rokneltayb.databinding.ItemHomePupularProductsBinding

class HomeProductsAdapter (
    private val itemClick: (PopularProduct) -> Unit
) :  ListAdapter<PopularProduct, HomeProductsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHomePupularProductsBinding.inflate(LayoutInflater.from(parent.context)), itemClick)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }

    class ViewHolder(private val binding: ItemHomePupularProductsBinding, private val itemClick: (PopularProduct) -> Unit):RecyclerView.ViewHolder(binding.root){
        @RequiresApi(Build.VERSION_CODES.P)
        fun bind(product: PopularProduct) {
            binding.root.setOnClickListener {
                itemClick(product)
            }
            
            Glide.with(binding.root.context).load(product.image).into(binding.imageProductImageView)
            binding.nameProductTextView.text = product.title
            if (product.discountValue != null && product.isDiscount == "active"){
                binding.discountTextView.text = product.discountValue
                binding.discountTextView.paintFlags = binding.discountTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
            else
                binding.discountTextView.visibility = View.INVISIBLE

            if (product.price != null)
                binding.priceTextView.text = product.price
            else
                binding.priceTextView.text = "500 KWD"

            binding.rateTextView.text = product.rate.toString()


        }

    }
    companion object DiffCallback : DiffUtil.ItemCallback<PopularProduct>() {
        override fun areItemsTheSame(oldItem: PopularProduct, newItem: PopularProduct): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PopularProduct, newItem: PopularProduct): Boolean {
            return oldItem.id == newItem.id
        }
    }
}