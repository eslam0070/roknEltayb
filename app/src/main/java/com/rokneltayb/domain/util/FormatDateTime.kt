package com.rokneltayb.domain.util

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.rokneltayb.data.sharedPref.SharedPreferencesImpl
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object FormatDateTime {

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateWithTimeWithApi26(context:Context,date: String, textView: TextView) {
        val parsedDate = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)
        val formattedDate =
            if (SharedPreferencesImpl(context).getLanguage() == Constants.LANGUAGE_ARABIC) {
                parsedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss", Locale("ar")))
            } else {
                parsedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss", Locale.ENGLISH))
            }
        textView.text = formattedDate
    }

    fun dateWithTimeWithApiLow(context:Context,date: String, textView: TextView) {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
        val formatter =
            if (SharedPreferencesImpl(context).getLanguage() == Constants.LANGUAGE_ARABIC) {
                SimpleDateFormat("yyyy-MM-dd' T 'HH:mm:ss", Locale("ar"))
            } else {
                SimpleDateFormat("yyyy-MM-dd' T 'HH:mm:ss", Locale.ENGLISH)
            }
        val formattedDate = formatter.format(parser.parse(date))
        textView.text = formattedDate.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateWithApi26(context:Context,date: String, textView: TextView) {
        val parsedDate = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)
        val formattedDate =
            if (SharedPreferencesImpl(context).getLanguage() == Constants.LANGUAGE_ARABIC) {
                parsedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale("ar")))
            } else {
                parsedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH))
            }

        textView.text = formattedDate
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateWithApi262(context:Context,date: String, textView: TextView) {
        val parsedDate = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)
        val formattedDate =
            if (SharedPreferencesImpl(context).getLanguage() == Constants.LANGUAGE_ARABIC) {
                parsedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale("ar")))
            } else {
                parsedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH))
            }
        textView.text = formattedDate
    }

    fun dateWithApiLow(context:Context,date: String, textView: TextView) {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
//        val formatter = SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH)

        val formatter =
            if (SharedPreferencesImpl(context).getLanguage() == Constants.LANGUAGE_ARABIC) {
                SimpleDateFormat("dd-MM-yyyy", Locale("ar"))
            } else {
                SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
            }
        val formattedDate = formatter.format(parser.parse(date))
        textView.text = formattedDate.toString()
    }

    fun dateWithApiLow2(context:Context,date: String, textView: TextView) {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
        val formatter =
            if (SharedPreferencesImpl(context).getLanguage() == Constants.LANGUAGE_ARABIC) {
                SimpleDateFormat("dd-MM-yyyy", Locale("ar"))
            } else {
                SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
            }
        val formattedDate = formatter.format(parser.parse(date))
        textView.text = formattedDate.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formattedDateParsing(context:Context,date: String, textView: TextView) {
        val parser: SimpleDateFormat
        val formatter: SimpleDateFormat
        if (SharedPreferencesImpl(context).getLanguage() == Constants.LANGUAGE_ARABIC) {
            parser = SimpleDateFormat("yyyy-MM-dd", Locale("ar"))
            formatter = SimpleDateFormat("yyyy-MM-dd", Locale("ar"))
        } else {
            parser = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        }
        val formattedDate = formatter.format(parser.parse(date))
        textView.text = formattedDate.toString()
    }

    fun formattedDateParsingEnglish(date: String): String {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

        val formattedDate = formatter.format(parser.parse(date))
        return formattedDate.toString()
    }

    fun formatDate(context:Context,date: String): String {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
        val formatter =
            if (SharedPreferencesImpl(context).getLanguage() == Constants.LANGUAGE_ARABIC) {
                SimpleDateFormat("dd-MM-yyyy", Locale("ar"))
            } else {
                SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
            }
        val formattedDate = formatter.format(parser.parse(date))
        return formattedDate.toString()
    }

    fun formattedDateParsing(context:Context,date: String): String {
        val parser: SimpleDateFormat
        val formatter: SimpleDateFormat
        if (SharedPreferencesImpl(context).getLanguage() == Constants.LANGUAGE_ARABIC) {
            parser = SimpleDateFormat("yyyy-MM-dd", Locale("ar"))
            formatter = SimpleDateFormat("yyyy-MM-dd", Locale("ar"))
        } else {
            parser = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        }
        val formattedDate = formatter.format(parser.parse(date))
        return formattedDate.toString()
    }

}