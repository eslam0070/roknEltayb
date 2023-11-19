package com.rokneltayb.core.appUtils

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object FormatDateTime {

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateWithTimeWithApi26(date:String, textView: TextView){
        val parsedDate  = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)
        val formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy   HH:mm a", Locale.ENGLISH))
        textView.text = formattedDate
    }

    fun dateWithTimeWithApiLow(date:String, textView: TextView){
        val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.ENGLISH)
        val formatter = SimpleDateFormat("dd-MM-yyyy   HH:mm a",Locale.ENGLISH)
        val formattedDate = formatter.format(parser.parse(date))
        textView.text = formattedDate.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateWithApi26(date:String, textView: TextView){
        val parsedDate  = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)
        val formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH))
        textView.text = formattedDate
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateWithApi262(date:String, textView: TextView){
        val parsedDate  = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)
        val formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH))
        textView.text = formattedDate
    }
    fun dateWithApiLow(date:String, textView: TextView){
        val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.ENGLISH)
        val formatter = SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH)
        val formattedDate = formatter.format(parser.parse(date))
        textView.text = formattedDate.toString()
    }

    fun dateWithApiLow2(date:String, textView: TextView){
        val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.ENGLISH)
        val formatter = SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH)
        val formattedDate = formatter.format(parser.parse(date))
        textView.text = formattedDate.toString()
    }

    //===============================================================================
    //========================================   Date ===============================
    //===============================================================================


    fun getCurrentDate(): String {
        val todayDate: Date = Calendar.getInstance().getTime()
        val formatter = java.text.SimpleDateFormat("yyyy-MM-dd hh:mm")
        return formatter.format(todayDate)
    }
}