package com.rokneltayb.domain.util

import android.content.Context
import android.location.Address
import android.location.Geocoder
import java.io.IOException
import java.util.ArrayList
import java.util.Locale

object Constants {

    const val USER_ID_KEY = "user_id"
    const val USER_TOKEN_KEY = "user_token"

    const val LANGUAGE_ENGLISH = "en"
    const val LANGUAGE_ARABIC = "ar"

    val densities: ArrayList<Float> = object : ArrayList<Float>() {
        init {
            add(0.75f)
            add(1.0f)
            add(1.5f)
            add(2.0f)
            add(3.0f)
            add(4.0f)
        }
    }
    fun getAddress(lat: Double, lng: Double, ctx: Context?): String? {
        val addresses: List<Address>?
        val geocoder = Geocoder(ctx!!, Locale("ar"))
        return try {
            addresses = geocoder.getFromLocation(
                lat,
                lng,
                1
            ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses!!.size > 0) {
                val address = addresses[0].getAddressLine(0).replace("Unnamed Road", "")
                // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                val city = addresses[0].locality
                val state = addresses[0].adminArea
                val country = addresses[0].countryName
                val postalCode = addresses[0].postalCode
                val knownName = addresses[0].featureName // Only if available else return NULL
                address
            } else {
                "غير معروف"
            }
        } catch (e: IOException) {
            null
        }
    }
    const val mobilePhoneDigitsNumber = 10
    const val passwordCharNumber = 8
}