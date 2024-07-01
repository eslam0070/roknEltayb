package com.rokneltayb.data.model.cart.delivery

data class DeliveryTime(
    val id: Int,
    val title: String
){
    override fun toString(): String {
        return title
    }
}