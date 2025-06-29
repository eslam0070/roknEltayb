package com.rokneltayb.presentation.home.search

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
import com.rokneltayb.R
import com.rokneltayb.data.model.favorite.FavoritesData
import com.rokneltayb.data.model.home.home.PopularProduct
import com.rokneltayb.data.model.products.DataXX
import com.rokneltayb.data.sharedPref.SharedPreferencesImpl
import com.rokneltayb.databinding.ItemFavoritesBinding
import com.rokneltayb.databinding.ItemHomePupularProductsBinding
import com.rokneltayb.databinding.ItemSearchHomeBinding
import com.rokneltayb.domain.util.toast
import java.text.DecimalFormat

class SearchAdapter(
    private val itemClick: (Int) -> Unit,
    private val addToCartItemClick: (DataXX) -> Unit
) :  ListAdapter<DataXX, SearchAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSearchHomeBinding.inflate(LayoutInflater.from(parent.context)), itemClick,addToCartItemClick)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    class ViewHolder(private val binding: ItemSearchHomeBinding, private val itemClick: (Int) -> Unit,
                     private val addToCartItemClick: (DataXX) -> Unit):RecyclerView.ViewHolder(binding.root){
        @RequiresApi(Build.VERSION_CODES.P)
        fun bind(product: DataXX) {
            binding.root.setOnClickListener {
                itemClick(product.id!!)
            }

            binding.addToCartButton.setOnClickListener {
                addToCartItemClick(product)
            }

            if (product.inStock == 0)
                binding.unavailableTextView.visibility = View.VISIBLE
            else
                binding.unavailableTextView.visibility = View.GONE



            Glide.with(binding.root.context).load(product.image).into(binding.imageFavoriteImageView)


            binding.nameProductTextView.text = product.title

            binding.priceTextView.text = product.price + binding.root.context.getString(R.string.kwd)

        }

    }
    companion object DiffCallback : DiffUtil.ItemCallback<DataXX>() {
        override fun areItemsTheSame(oldItem: DataXX, newItem: DataXX): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DataXX, newItem: DataXX): Boolean {
            return oldItem.id == newItem.id
        }
    }
}