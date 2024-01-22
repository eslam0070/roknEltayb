package com.rokneltayb.data.model.address

data class AddressData(
    val address: String?=null,
    val apartment: String?=null,
    val avenue: String?=null,
    val block: String?=null,
    val building_num: String?=null,
    val city_id: Int?=null,
    val created_at: String?=null,
    val floor_num: String?=null,
    val id: Int?=null,
    val lat: String?=null,
    val lng: String?=null,
    val name: String?=null,
    val phone: String?=null,
    val street: String?=null,
    val updated_at: String?=null,
    val user_id: Int?=null
){
    override fun toString(): String {
        return name!!
    }
}