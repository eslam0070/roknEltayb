package com.rokneltayb.data.model.home.home


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

@Parcelize
data class Data(
    @SerializedName("Categories")
    @Expose
    val categories: List<Category?>?,
    @SerializedName("daily_products")
    @Expose
    val dailyProducts: List<PopularProduct?>?,
    @SerializedName("Popular_products")
    @Expose
    val popularProducts: List<PopularProduct?>?,
    @SerializedName("Slider")
    @Expose
    val slider: List<Slider?>?
) : Parcelable