package com.rokneltayb.core.appUtils

import android.accounts.AccountManager
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.icu.text.SimpleDateFormat
import android.location.LocationManager
import android.os.BatteryManager
import android.os.Build
import android.util.Patterns
import android.util.Patterns.EMAIL_ADDRESS
import android.webkit.URLUtil
import java.util.regex.Pattern

object Validate {
    // public static String appColor = "#9966ff";
    //<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    //    public static boolean isInternetAvailable() {
    //        try {
    //            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name
    //            return ipAddr.equals("") ? false : true;
    //        } catch (Exception e) {
    //            return false;
    //        }
    //      }
    //<uses-permission android:name="android.permission.GET_ACCOUNTS" />
    fun deviceHasGoogleAccount(context: Context?): Boolean {
        val accMan = AccountManager.get(context)
        val accArray = accMan.getAccountsByType("com.google")
        return if (accArray.size >= 1) true else false
    }

    fun isNumeric(str: String): Boolean {
        try {
            str.toDouble()
        } catch (nfe: NumberFormatException) {
            return false
        }
        return true
    }

    @JvmStatic
    fun isEmpty(str: String?): Boolean {
        return if (str == null || str.isEmpty()) true else false
    }

    fun isAvLen(str: String, from: Int, to: Int): Boolean {
        return if (str.length >= from && str.length <= to) true else false
    }

    fun isEqual(str1: String?, str2: String?): Boolean {
        return if (str1 == null || str2 == null) false else if (str1 == str2) true else false
    }

    //Different between URL and NetworkURL http://stackoverflow.com/questions/23866700/difference-between-isnetworkurl-and-isvalidurl
    fun isUrl(url: String?): Boolean {
        return URLUtil.isValidUrl(url)
    }

    fun isNetworkUrl(url: String?): Boolean {
        return URLUtil.isNetworkUrl(url)
    }

    fun haveParticularChars(str: String, chars: CharArray): Boolean {
        for (i in chars.indices) if (str.contains("" + chars[i])) return true
        return false
    }

    fun isMail(str: CharSequence?): Boolean {
        return if (str == null) false else Patterns.EMAIL_ADDRESS.matcher(str).matches()
    }

    fun isIPAddress(str: CharSequence?): Boolean {
        return if (str == null) false else Patterns.IP_ADDRESS.matcher(str).matches()
    }

    @JvmStatic
    fun isPhone(str: CharSequence?): Boolean {
        return if (str == null) false else Patterns.PHONE.matcher(str).matches()
    }

    //<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    fun isLocationEnabled(context: Context): Boolean {
        val lm: LocationManager
        var gps_enabled = false
        var network_enabled = false
        lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (ex: Exception) {
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (ex: Exception) {
        }
        return gps_enabled || network_enabled
    }

    //<uses-permission android:name="android.permission.BLUETOOTH"  android:required="false" />
    val isBluetoothEnabled: Boolean
        get() {
            val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            return mBluetoothAdapter?.isEnabled ?: true
        }

    fun batteryLevel(context: Context): Int {
        val intent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val level = intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
        val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100)
        return level * 100 / scale
    }

    fun isEndTimeLarger(startTime: String?, endTime: String?): Boolean {
        var elapsed: Long = 0
        try {
            val sdf = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                SimpleDateFormat("HH:mm")
            } else {
                TODO("VERSION.SDK_INT < N")
            }
            val d1 = sdf.parse(startTime)
            val d2 = sdf.parse(endTime)
            elapsed = d2.time - d1.time
        } catch (e: Exception) {
            e.stackTrace
        }
        return elapsed > 0
    }

    fun isEmailValid(email: String): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    fun checkEmail(email: String): Boolean {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches()
    }

    fun checkEmail2(email: String): Boolean {
        return EMAIL_ADDRESS.matcher(email).matches()
    }



}