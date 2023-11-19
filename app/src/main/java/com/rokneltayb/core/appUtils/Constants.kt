package com.rokneltayb.core.appUtils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.database.Cursor
import android.location.Address
import android.location.Geocoder
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi
import com.rokneltayb.R

import java.io.IOException
import java.util.Locale


@SuppressLint("NewApi")
object Constants {



    const val SPLASH_DISPLAY_TIME:Long = 4000
    const val img_base_url = "https://newmonasbh.multi-kw.com/image/"

    //RequestsCodes
    var FILE_TYPE_IMAGE = 1001
    var FILE_TYPE_PDF = 1002
    var FILE_TYPE_AUDIO = 1003
    const val FAILED = "400"
    const val NOT_AUTOIZE = "401"
    const val NOT_ACCEPTABLE = 403
    const val NOT_FOUND = 404
    const val NOT_ACTIVE = 405
    const val UNKNOWN_ERROR_SERVER = 500


    const val PERMISSION_STORAGE_IMAGES = 1315
    const val CHOOSE_FILE_REQUEST = 9544
    const val CAMERA_REQUEST = 1888
    const val MY_CAMERA_PERMISSION_CODE = 100
    const val VERSION_APIS = 1
    const val CULTURE = "ar-sa"
    var isHomeStatus = false
    var isFavoriteStatus = false
    var isErrorFire = false
    const val INTENT_PAGE = "fragment_name"
    const val INTENT_BUNDLE = "bundle_name"
    const val PHONE = "phone"
    const val CHECK = "check"

    //LANGUAGE
    const val LANGUAGE_ENGLISH = "en"
    const val LANGUAGE_ARABIC = "ar"
    const val UserType= "user_type"

    const val IS_BACK_KEY_PRESSED = "IS_BACK_KEY_PRESSED"

    const val SITE_KEY = "6LddIywmAAAAAAyXKyE2taAC_-IUYby-RJxtX6sB"
    const val SECRET_KEY = "6LddIywmAAAAAP-j8Llno-yQFfrVwOWQGz702q6_"


    //sharedPref
    const val IS_LOGIN = "is_login"
    const val PUSH_TOKEN = "PUSH_TOKEN"
    const val USER_LOGIN = "user_login"

    const val MAIL_ID ="MAIL_ID"
    const val MAIL_PROJECT_ID = "MAIL_PROJECT_ID"
    const val MAIL_PROJECT_NAME = "MAIL_PROJECT_NAME"
    const val MAIL_MESSAGE = "MAIL_MESSAGE"
    const val MAIL_MESSAGE_TITLE = "MAIL_MESSAGE_TITLE"
    const val MAIL_DATE = "MAIL_DATA"
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

  //  val CONTAINER_MAIN_ACTIVITY_ID: Int = R.id.cl_main_activity_fragment_container

    //Base Activity
    const val LOGIN_PAGE = 1
    const val SIGN_UP_PAGE = 2

    const val PUSHER_APP_ID= "1683923"
    const val PUSHER_APP_KEY="16a8950cb1084e062cc7"
    const val PUSHER_APP_SECRET="a5ca7b635b234590ce33"
    const val PUSHER_APP_CLUSTER="eu"
    const val EVENT = "App\\Events\\chatEvent"
    const val CHANNEL = "MessageSent-channel-"
    fun formatAddress(loc: String?): String {
        var loc = loc
        return if (loc != null) {
            loc = loc.replace(",Unnamed Road", "")
                .replace("،Unnamed Road", "")
                .replace("Unnamed Road", "شارع غير معروف")
            if (loc.contains(",")) {
                loc.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[0] + "%%" + loc.substring(loc.indexOf(",") + 1)
            } else {
                loc.split("،".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[0] + "%%" + loc.substring(loc.indexOf("،") + 1)
            }
        } else {
            "شارع غير معروف" + "%%" + ""
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

    fun goToMap(lat: Double, lng: Double, ctx: Context) {
        val intent = Intent(
            Intent.ACTION_VIEW, Uri.parse(
                "geo:<$lat>,<$lng>?q=<$lat>,<$lng>(الموقع المطلوب)"
            )
        )
        ctx.startActivity(intent)
    }
    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    fun checkVideoDuration(context:Context,selectedVieo: Uri?): Boolean {
        val filePathColumn = arrayOf(MediaStore.Video.Media.DATA)
        val cursor: Cursor = context.contentResolver.query(
            selectedVieo!!,
            filePathColumn, null, null, null
        )!!
        cursor.moveToFirst()
        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
        val filePath = cursor.getString(columnIndex)
        cursor.close()
        try {
            if (filePath != null) {
                val mp: MediaPlayer = MediaPlayer.create(context, Uri.parse(filePath))
                val duration = mp.duration
                mp.release()
                return if (duration / 1000 > 30) {
                    Log.i("testCheckDuration", "duration is more 5: ")
                    false
                } else {
                    Log.i("testCheckDuration", "duration is ok: ")
                    true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Log.i("testCheckDuration", "return here: ")
        return false
    }

}