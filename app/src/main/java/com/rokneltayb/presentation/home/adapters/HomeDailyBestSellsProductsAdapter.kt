package com.rokneltayb.presentation.home.adapters

import android.graphics.Paint
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rokneltayb.R
import com.rokneltayb.data.model.home.home.PopularProduct
import com.rokneltayb.databinding.ItemHomeBestSellsProductsBinding
import com.rokneltayb.databinding.ItemHomePupularProductsBinding

class HomeDailyBestSellsProductsAdapter(
    private val itemClick: (PopularProduct) -> Unit,
    private val cartItemClick: (Int, PopularProduct, Int) -> Unit,
    private val removeCartItemClick: (Int, PopularProduct) -> Unit,
    private val plusCartItemClick: (Int, Int, PopularProduct) -> Unit,
    private val munisItemClick: (Int, Int, PopularProduct) -> Unit,
    private val favoriteItemClick: (Int,PopularProduct,Boolean) -> Unit
) :  ListAdapter<PopularProduct, HomeDailyBestSellsProductsAdapter.ViewHolder>(DiffCallback) {
    var count:Int? = 1
    val isFavorite = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHomeBestSellsProductsBinding.inflate(LayoutInflater.from(parent.context)),  itemClick,cartItemClick,removeCartItemClick,plusCartItemClick,munisItemClick,favoriteItemClick)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(position,product,count,isFavorite)
    }

    class ViewHolder(private val binding: ItemHomeBestSellsProductsBinding, private val itemClick: (PopularProduct) -> Unit
                     ,private val cartItemClick: (Int, PopularProduct, Int) -> Unit,
                     private val removeCartItemClick: (Int, PopularProduct) -> Unit,
                     private val plusCartItemClick: (Int, Int, PopularProduct) -> Unit,
                     private val munisItemClick: (Int, Int, PopularProduct) -> Unit,
                     private val favoriteItemClick: (Int,PopularProduct,Boolean) -> Unit):RecyclerView.ViewHolder(binding.root){
        @RequiresApi(Build.VERSION_CODES.P)
        fun bind(position: Int,product: PopularProduct,count: Int?, isFavorite2: Boolean) {

            val zoomInAnim: Animation = AnimationUtils.loadAnimation(binding.root.context, R.anim.zoom_in)
            val zoomOutAnim: Animation = AnimationUtils.loadAnimation(binding.root.context, R.anim.zoom_out)
            var isFavorite = isFavorite2
            var total = 1

            binding.root.setOnClickListener {
                itemClick(product)
            }

            binding.addFavoriteImageView.setOnClickListener {
                if (isFavorite){
                    binding.addFavoriteImageView.setImageResource(R.drawable.deletefavourite)
                    binding.addFavoriteImageView.startAnimation(zoomOutAnim)
                }
                else
                    binding.addFavoriteImageView.setImageResource(R.drawable.addfavourite)

                binding.addFavoriteImageView.startAnimation(zoomInAnim)
                isFavorite = !isFavorite


                favoriteItemClick(position,product,isFavorite)
            }

            binding.addToCartImageView.setOnClickListener {
                binding.addToCartImageView.visibility = View.GONE
                binding.plusImageView.visibility = View.VISIBLE
                binding.countTextView.visibility = View.VISIBLE
                binding.miunsImageView.visibility = View.GONE
                binding.removeImageView.visibility = View.VISIBLE
                cartItemClick(position,product,1)
            }
            binding.countTextView.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    total = 1
                    total = binding.countTextView.text.toString().toInt()
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })

            binding.plusImageView.setOnClickListener {
                total = binding.countTextView.text.toString().toInt()
                total++
                if (total > 1){
                    binding.removeImageView.visibility = View.GONE
                    binding.miunsImageView.visibility = View.VISIBLE
                }
                else{
                    binding.removeImageView.visibility = View.VISIBLE
                    binding.miunsImageView.visibility = View.GONE
                }

                binding.countTextView.text = total.toString()

                plusCartItemClick(1,position,product)
            }

            binding.miunsImageView.setOnClickListener {
                if (total > 1) {
                    binding.removeImageView.visibility = View.GONE
                    total = binding.countTextView.text.toString().toInt()
                    total--
                    binding.countTextView.text = total.toString()
                }

                if (total > 1)
                    binding.removeImageView.visibility = View.GONE
                else
                    binding.removeImageView.visibility = View.VISIBLE

                munisItemClick(total,position,product)
            }

            binding.removeImageView.setOnClickListener {
                binding.addToCartImageView.visibility = View.VISIBLE
                binding.removeImageView.visibility = View.GONE
                binding.plusImageView.visibility = View.GONE
                binding.countTextView.visibility = View.GONE
                binding.miunsImageView.visibility = View.GONE
                removeCartItemClick(position,product)
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
                binding.priceTextView.text = product.price.toString()
            else
                binding.priceTextView.text = "500 KWD"

            binding.rateTextView.text = product.rate.toString()


            if (product.isFavorite == 0)
                binding.addFavoriteImageView.setImageResource(R.drawable.deletefavourite)
            else
                binding.addFavoriteImageView.setImageResource(R.drawable.addfavourite)


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