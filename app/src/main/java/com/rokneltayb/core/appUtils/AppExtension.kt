//package com.exas.qpmoemp.core.appUtils
//
//import androidx.fragment.app.Fragment
//import com.osul.qdpmapp.core.base.BaseActivity
//import com.osul.qdpmapp.core.cache.Cache
//import com.osul.qdpmapp.core.network.MyApplication
//
//
//fun getCacheInstance(): Cache {
//    if(MyApplication.cache == null){
//        MyApplication.cache =  Cache()
//    }
//    return MyApplication.cache!!
//}
//
//
//
//fun Fragment.showProgress() {
//    if (activity != null)
//        (requireActivity() as BaseActivity).showProgress(true)
//}
//
//fun Fragment.hideProgress() {
//    if (activity != null)
//        (requireActivity() as BaseActivity).showProgress(false)
//}
//
//
//fun getSharedPrefInstance(): SharedPrefUtils {
//    return if (MyApplication.sharedPrefUtils == null) {
//        MyApplication.sharedPrefUtils = SharedPrefUtils()
//        MyApplication.sharedPrefUtils!!
//    } else {
//        MyApplication.sharedPrefUtils!!
//    }
//}