package com.rokneltayb.data.model.cart

data class CountModel(val id:Int,val number:String){
    override fun toString(): String {
        return number
    }
}
