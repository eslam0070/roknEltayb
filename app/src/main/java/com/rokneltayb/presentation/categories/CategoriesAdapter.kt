package com.rokneltayb.presentation.categories

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rokneltayb.data.model.categories.Category
import com.rokneltayb.databinding.ItemCategoriesBinding

class CategoriesAdapter (
    private val itemClick: (Category) -> Unit
) :  ListAdapter<Category, CategoriesAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context)), itemClick)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }

    class ViewHolder(private val binding: ItemCategoriesBinding,
                     private val itemClick: (Category) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root){
        @RequiresApi(Build.VERSION_CODES.P)
        fun bind(category: Category) {

            binding.root.setOnClickListener {
                itemClick(category)
            }
            
            Glide.with(binding.root.context).load(category.icon).into(binding.categoryImageView)
            binding.nameTextView.text = category.title
        }

    }
    companion object DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }
    }
}