package com.rokneltayb.data.model.address.city

data class City(
    val delivery_cost: Int,
    val id: Int,
    val title: String
){
    override fun toString(): String {
        return title
    }
}