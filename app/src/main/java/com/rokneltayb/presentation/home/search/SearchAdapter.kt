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
import com.rokneltayb.data.model.products.Product
import com.rokneltayb.data.sharedPref.SharedPreferencesImpl
import com.rokneltayb.databinding.ItemFavoritesBinding
import com.rokneltayb.databinding.ItemHomePupularProductsBinding
import com.rokneltayb.databinding.ItemSearchHomeBinding
import java.text.DecimalFormat

class SearchAdapter(
    private val itemClick: (Int) -> Unit,
    private val addToCartItemClick: (Product) -> Unit
) :  ListAdapter<Product, SearchAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSearchHomeBinding.inflate(LayoutInflater.from(parent.context)), itemClick,addToCartItemClick)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    class ViewHolder(private val binding: ItemSearchHomeBinding, private val itemClick: (Int) -> Unit,
                     private val addToCartItemClick: (Product) -> Unit):RecyclerView.ViewHolder(binding.root){
        @RequiresApi(Build.VERSION_CODES.P)
        fun bind(product: Product) {
            binding.root.setOnClickListener {
                itemClick(product.id!!)
            }

            binding.addToCartButton.setOnClickListener {
                addToCartItemClick(product)
            }


            Glide.with(binding.root.context).load(product.image).into(binding.imageFavoriteImageView)


            binding.nameProductTextView.text = product.title

            binding.priceTextView.text = product.price + binding.root.context.getString(R.string.kwd)

        }

    }
    companion object DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }
    }
}