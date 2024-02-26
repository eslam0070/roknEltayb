package com.rokneltayb.data.model.address.details


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class AddressDetailsData(
    @SerializedName("address")
    @Expose
    val address: Any?,
    @SerializedName("apartment")
    @Expose
    val apartment: String?,
    @SerializedName("avenue")
    @Expose
    val avenue: String?,
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
    val lat: Any?,
    @SerializedName("lng")
    @Expose
    val lng: Any?,
    @SerializedName("name")
    @Expose
    val name: String?,
    @SerializedName("phone")
    @Expose
    val phone: String?,
    @SerializedName("street")
    @Expose
    val street: String?,
    @SerializedName("updated_at")
    @Expose
    val updatedAt: String?,
    @SerializedName("user_id")
    @Expose
    val userId: Int?
)