package com.rokneltayb.presentation.favorite

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
import com.rokneltayb.data.model.favorite.FavoritesData
import com.rokneltayb.data.model.home.home.PopularProduct
import com.rokneltayb.databinding.ItemFavoritesBinding
import com.rokneltayb.databinding.ItemHomePupularProductsBinding

class FavoritesAdapter(
    private val itemClick: (Int) -> Unit,
    private val addToCartItemClick: (FavoritesData) -> Unit,
    private val deleteFavoriteItemClick: (Int) -> Unit
) :  ListAdapter<FavoritesData, FavoritesAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemFavoritesBinding.inflate(LayoutInflater.from(parent.context)), itemClick,addToCartItemClick,deleteFavoriteItemClick)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favorite = getItem(position)
        holder.bind(favorite)
    }

    class ViewHolder(private val binding: ItemFavoritesBinding, private val itemClick: (Int) -> Unit,
                     private val addToCartItemClick: (FavoritesData) -> Unit,
                     private val deleteFavoriteItemClick: (Int) -> Unit):RecyclerView.ViewHolder(binding.root){
        @RequiresApi(Build.VERSION_CODES.P)
        fun bind(favorte: FavoritesData) {
            binding.root.setOnClickListener {
                itemClick(favorte.id!!)
            }

            binding.addToCartButton.setOnClickListener {
                addToCartItemClick(favorte)
            }

            binding.deleteFavoriteImageView.setOnClickListener {
                deleteFavoriteItemClick(favorte.id!!)
            }

            Glide.with(binding.root.context).load(favorte.image).into(binding.imageFavoriteImageView)


            binding.nameProductTextView.text = favorte.title

            if (favorte.price != null)
                binding.priceTextView.text = favorte.price
            else
                binding.priceTextView.text = "500 KWD"
        }

    }
    companion object DiffCallback : DiffUtil.ItemCallback<FavoritesData>() {
        override fun areItemsTheSame(oldItem: FavoritesData, newItem: FavoritesData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: FavoritesData, newItem: FavoritesData): Boolean {
            return oldItem.id == newItem.id
        }
    }
}