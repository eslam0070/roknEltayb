package com.rokneltayb.presentation.home.details.rate

import android.content.Context
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
import com.rokneltayb.data.model.home.home.PopularProduct
import com.rokneltayb.data.model.products.details.Data
import com.rokneltayb.databinding.ItemHomePupularProductsBinding
import com.rokneltayb.databinding.RateItemBinding

class RateAdapter() :  ListAdapter<Data.Rates, RateAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RateItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rate = getItem(position)
        holder.bind(rate)
    }

    class ViewHolder(private val binding: RateItemBinding):RecyclerView.ViewHolder(binding.root){
        @RequiresApi(Build.VERSION_CODES.P)
        fun bind(rate: Data.Rates) {
            binding.nameUserRateTextView.text = rate.user_name
            binding.dateRateTextView.text = rate.date
            binding.rateBar.rating = rate.rate.toFloat()
            binding.desctiptionRateTextView.text = rate.description
        }

    }
    companion object DiffCallback : DiffUtil.ItemCallback<Data.Rates>() {
        override fun areItemsTheSame(oldItem: Data.Rates, newItem: Data.Rates): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Data.Rates, newItem: Data.Rates): Boolean {
            return oldItem.id == newItem.id
        }
    }
}