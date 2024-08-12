package com.rokneltayb.presentation.cart

import android.R
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import com.rokneltayb.databinding.ItemCartBinding
import kotlin.time.times


class CartAdapter(val viewmodel:CartViewModel,private val itemClick: (Int,Cart) -> Unit) :  ListAdapter<Cart, CartAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCartBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            ),viewmodel,itemClick
        )

    }
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cart = getItem(position)
        holder.bind(cart)
    }


    class ViewHolder(private val binding: ItemCartBinding,private val viewmodel:CartViewModel,private val itemClick: (Int,Cart) -> Unit
    ):


        RecyclerView.ViewHolder(binding.root){
        val list: ArrayList<CountModel> = ArrayList()

        @RequiresApi(Build.VERSION_CODES.P)
        fun bind(cart: Cart) {
            binding.deleteCartImageView.setOnClickListener {
                itemClick(1,cart)
            }

            binding.moveToWishListTextView.setOnClickListener {
                itemClick(2,cart)
            }

            Glide.with(binding.root.context).load(cart.product_image).into(binding.imageCartImageView)
            binding.nameCartTextView.text = cart.product_title
            val count = cart.price * cart.count

            binding.priceCartTextView.text = count.toString() + binding.root.context.getString(com.rokneltayb.R.string.kwd)



            addItemSpiner(cart)
        }

        private fun addItemSpiner(cart: Cart) {
            var selectNumver:Int? = null

            list.add(CountModel(1,"1"))
            list.add(CountModel(2,"2"))
            list.add(CountModel(3,"3"))
            list.add(CountModel(4,"4"))
            list.add(CountModel(5,"5"))
            list.add(CountModel(9,"6"))
            list.add(CountModel(7,"7"))
            list.add(CountModel(8,"8"))
            list.add(CountModel(9,"9"))
            list.add(CountModel(10,"10"))


            val dataAdapter: ArrayAdapter<CountModel> = ArrayAdapter<CountModel>(
                binding.root.context,
                com.rokneltayb.R.layout.shape_spinner_textview_align2, list
            )
            dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            binding.countSpinner.adapter = dataAdapter


            binding.countSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if (cart.count != dataAdapter.getItem(binding.countSpinner.selectedItemPosition)!!.number.toInt()){
                        viewmodel.updateCart(cart.product_id.toString(),cart.shape_id.toString(), dataAdapter.getItem(binding.countSpinner.selectedItemPosition)!!.number)
                        val count = dataAdapter.getItem(binding.countSpinner.selectedItemPosition)!!.number.toInt() * cart.price

                        if (count.toString().length > 4){
                            binding.priceCartTextView.text = count.toString().substring(0,3) + binding.root.context.getString(com.rokneltayb.R.string.kwd)
                        }else
                            binding.priceCartTextView.text = count.toString() + binding.root.context.getString(com.rokneltayb.R.string.kwd)


                        Log.d("TAG", "onaaItemSelected: "+count.toString())
                        Log.d("TAG", "onaaItemSelected: select "+dataAdapter.getItem(binding.countSpinner.selectedItemPosition)!!.number.toInt())


                    }

                }
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

            binding.countSpinner.setSelection(getCountSpinner(cart.count))
            Log.d("TAG", "onItemSelected: se"+selectNumver)
            Log.d("TAG", "onItemSelected: price"+cart.price)
        }

        private fun getCountSpinner(count:Int): Int {
            for (item : CountModel in list)
                if (item.id == count)
                    return list.indexOf(item)
            return 0
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Cart>() {
        override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            return oldItem.id == newItem.id
        }
    }


}