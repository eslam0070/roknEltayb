package com.rokneltayb.data.model.address

data class AddressResponse(
    val `data`: List<AddressData>,
    val message: String,
    val status: Int
)