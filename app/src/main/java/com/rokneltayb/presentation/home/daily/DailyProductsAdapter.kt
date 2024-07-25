package com.rokneltayb.presentation.home.daily

import android.graphics.Paint
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rokneltayb.R
import com.rokneltayb.data.model.products.DataXX
import com.rokneltayb.data.sharedPref.SharedPreferencesImpl
import com.rokneltayb.databinding.ItemProductsBinding
import com.rokneltayb.domain.util.toast
import com.rokneltayb.presentation.categories.products.ProductsAdapter
import java.text.DecimalFormat
import java.util.ArrayList

class DailyProductsAdapter(
    private val itemClick: (DataXX) -> Unit,
    private val cartItemClick: (Int, DataXX,Int) -> Unit,
    private val removeCartItemClick: (Int, DataXX) -> Unit,
    private val plusCartItemClick: (Int,Int, DataXX) -> Unit,
    private val munisItemClick: (Int,Int, DataXX) -> Unit,
    private val favoriteItemClick: (Int, DataXX,Boolean) -> Unit
) :  RecyclerView.Adapter<DailyProductsAdapter.ViewHolder>()  {

    var count:Int? = 1
    val isFavorite = false
    var productsModel: MutableList<DataXX> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemProductsBinding.inflate(LayoutInflater.from(parent.context)), itemClick,cartItemClick,removeCartItemClick,plusCartItemClick,munisItemClick,favoriteItemClick)
    }



    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productsModel[position]
        holder.bind(position,product,count,isFavorite)
    }



    class ViewHolder(private val binding: ItemProductsBinding, private val itemClick: (DataXX) -> Unit,
                     private val cartItemClick: (Int,DataXX,Int) -> Unit,
                     private val removeCartItemClick: (Int, DataXX) -> Unit,
                     private val plusCartItemClick: (Int,Int, DataXX) -> Unit,
                     private val munisItemClick: (Int,Int, DataXX) -> Unit,
                     private val favoriteItemClick: (Int,DataXX,Boolean) -> Unit):RecyclerView.ViewHolder(binding.root){
        @RequiresApi(Build.VERSION_CODES.P)
        fun bind(position: Int, product: DataXX, count: Int?, isFavorite2: Boolean) {
            val zoomInAnim: Animation = AnimationUtils.loadAnimation(binding.root.context, R.anim.zoom_in)
            val zoomOutAnim: Animation = AnimationUtils.loadAnimation(binding.root.context, R.anim.zoom_out)
            var isFavorite = isFavorite2
            var total = 1

            binding.root.setOnClickListener {
                itemClick(product)
            }




            binding.addFavoriteImageView.setOnClickListener {
                if (SharedPreferencesImpl(binding.root.context).getRememberMe()){
                    if (isFavorite){
                        binding.addFavoriteImageView.setImageResource(R.drawable.deletefavourite)
                        binding.addFavoriteImageView.startAnimation(zoomOutAnim)
                    }
                    else
                        binding.addFavoriteImageView.setImageResource(R.drawable.addfavourite)

                    binding.addFavoriteImageView.startAnimation(zoomInAnim)
                    isFavorite = !isFavorite

                    favoriteItemClick(position,product,isFavorite)
                }else
                    binding.root.context.toast(binding.root.context.getString(R.string.you_should_login))
            }

            if (product.inStock != 0){
                binding.unavailableTextView.visibility = View.GONE
                binding.addToCartImageView.setOnClickListener {
                    binding.addToCartImageView.visibility = View.GONE
                    binding.plusImageView.visibility = View.VISIBLE
                    binding.countTextView.visibility = View.VISIBLE
                    binding.miunsImageView.visibility = View.VISIBLE
                    binding.removeImageView.visibility = View.VISIBLE
                    cartItemClick(position,product,1)
                }
            }else{
                binding.unavailableTextView.visibility = View.VISIBLE
            }


            binding.plusImageView.setOnClickListener {
                if (total == product.inStock)
                    Toast.makeText(binding.root.context, "لقد وصلت الى الحد الاقصي للكميه المتاحة", Toast.LENGTH_SHORT).show()
                else{
                    total++
                    binding.countTextView.text = total.toString()
                    binding.removeImageView.visibility = View.GONE
                    binding.miunsImageView.visibility = View.VISIBLE
                    plusCartItemClick(total,position,product)
                }
            }

            binding.countTextView.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (total == product.inStock){
                        Toast.makeText(binding.root.context, "لقد وصلت الى الحد الاقصي للكميه المتاحة", Toast.LENGTH_SHORT).show()
                    }
                    else
                        total = binding.countTextView.text.toString().toInt()
                }
                override fun afterTextChanged(s: Editable?) {}
            })



            binding.miunsImageView.setOnClickListener {
                if (total > 1) {
                    binding.removeImageView.visibility = View.GONE
                    total = binding.countTextView.text.toString().toInt()
                    total--
                    binding.countTextView.text = total.toString()
                }

                if (total > 1)
                    binding.removeImageView.visibility = View.GONE
                else{
                    binding.removeImageView.visibility = View.VISIBLE
                    binding.miunsImageView.visibility = View.GONE
                }

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
            if (product.discount_value != null && product.is_discount == "active"){
                binding.discountTextView.text = product.discount_value.toString() + binding.root.context.getString(R.string.kwd)
                binding.discountTextView.paintFlags = binding.discountTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
            else
                binding.discountTextView.visibility = View.INVISIBLE


            binding.priceTextView.text = product.price + binding.root.context.getString(R.string.kwd)



            binding.rateTextView.text = product.rate.toString().substring(0,1)

            if (product.is_favorite == 0)
                binding.addFavoriteImageView.setImageResource(R.drawable.deletefavourite)
            else
                binding.addFavoriteImageView.setImageResource(R.drawable.addfavourite)
        }

    }

    override fun getItemCount(): Int {
        return productsModel.size

    }

    fun addList(list: List<DataXX>?) {
        productsModel.addAll(list!!)
        notifyDataSetChanged()
    }

    fun reset() {
        productsModel.clear()
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}