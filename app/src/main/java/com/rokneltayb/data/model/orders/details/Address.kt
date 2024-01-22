package com.rokneltayb.data.model.orders.details


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Address(
    @SerializedName("address")
    @Expose
    val address: String?,
    @SerializedName("apartment")
    @Expose
    val apartment: Any?,
    @SerializedName("avenue")
    @Expose
    val avenue: Any?,
    @SerializedName("block")
    @Expose
    val block: String?,
    @SerializedName("building_num")
    @Expose
    val buildingNum: String?,
    @SerializedName("city_id")
    @Expose
    val cityId: Int?,
    @SerializedName("created_at")
    @Expose
    val createdAt: String?,
    @SerializedName("floor_num")
    @Expose
    val floorNum: String?,
    @SerializedName("id")
    @Expose
    val id: Int?,
    @SerializedName("lat")
    @Expose
    val lat: String?,
    @SerializedName("lng")
    @Expose
    val lng: String?,
    @SerializedName("name")
    @Expose
    val name: String?,
    @SerializedName("phone")
    @Expose
    val phone: Any?,
    @SerializedName("street")
    @Expose
    val street: Any?,
    @SerializedName("updated_at")
    @Expose
    val updatedAt: String?,
    @SerializedName("user_id")
    @Expose
    val userId: Int?
)