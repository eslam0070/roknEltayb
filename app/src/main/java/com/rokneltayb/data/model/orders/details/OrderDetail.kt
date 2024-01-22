package com.rokneltayb.data.model.orders.details


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class OrderDetail(
    @SerializedName("ar_title")
    @Expose
    val arTitle: String?,
    @SerializedName("ar_title_shape")
    @Expose
    val arTitleShape: String?,
    @SerializedName("count")
    @Expose
    val count: Int?,
    @SerializedName("created_at")
    @Expose
    val createdAt: String?,
    @SerializedName("en_title")
    @Expose
    val enTitle: String?,
    @SerializedName("en_title_shape")
    @Expose
    val enTitleShape: String?,
    @SerializedName("id")
    @Expose
    val id: Int?,
    @SerializedName("item")
    @Expose
    val item: Item?,
    @SerializedName("note")
    @Expose
    val note: Any?,
    @SerializedName("order_id")
    @Expose
    val orderId: Int?,
    @SerializedName("price")
    @Expose
    val price: Int?,
    @SerializedName("product_id")
    @Expose
    val productId: Int?,
    @SerializedName("shape")
    @Expose
    val shape: Shape?,
    @SerializedName("shape_id")
    @Expose
    val shapeId: Int?,
    @SerializedName("shape_title")
    @Expose
    val shapeTitle: String?,
    @SerializedName("storage_id")
    @Expose
    val storageId: Any?,
    @SerializedName("title")
    @Expose
    val title: String?,
    @SerializedName("updated_at")
    @Expose
    val updatedAt: String?,
    @SerializedName("user_id")
    @Expose
    val userId: Any?
)