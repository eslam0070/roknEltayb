package com.rokneltayb.core.base

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import com.exas.qpmoemp.core.appUtils.localization.LocalizationUtils
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation
import com.rokneltayb.R
import com.rokneltayb.core.appUtils.Constants
import com.rokneltayb.data.sharedPref.SharedPreferencesImpl
import com.rokneltayb.databinding.ActivityBaseBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BaseActivity : AppCompatActivity() {

    private var progressDialog: Dialog? = null
    var dialogDismissThread: Job? = null
    var binding: ActivityBaseBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val configuration = resources.configuration
        configuration.fontScale = 1.toFloat() //0.85 small size, 1 normal size, 1,15 big etc
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = configuration.fontScale * metrics.density


        if (!Constants.densities.contains(metrics.density))
            configuration.densityDpi = (LocalizationUtils.getDensity(this) * 170f).toInt()
        resources.updateConfiguration(configuration, metrics)
        binding = DataBindingUtil.setContentView(this@BaseActivity, R.layout.activity_base)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding!!.bottomNavigation.add(CurvedBottomNavigation.Model(1, getString(R.string.home),R.drawable.ic_home))
        binding!!.bottomNavigation.add(CurvedBottomNavigation.Model(2, getString(R.string.categories),R.drawable.ic_home))
        binding!!.bottomNavigation.add(CurvedBottomNavigation.Model(3, getString(R.string.cart),R.drawable.ic_home))
        binding!!.bottomNavigation.add(CurvedBottomNavigation.Model(4, getString(R.string.more),R.drawable.ic_home))

        binding!!.bottomNavigation.setOnClickMenuListener {
            when(it.id){

            }
        }
    }

   /* override fun onBackPressed() {
        when (mNavController!!.currentDestination!!.id) {

            R.id.mailFragment, R.id.archiveFragment, R.id.settingsFragment, R.id.fragmentProjectDetails -> {
                mNavController?.navigate(R.id.homeNewFragment)
            }

            else -> {
                when (mNavController!!.currentDestination!!.id) {
                    R.id.mailDetailsFragment -> {
                        mNavController?.navigate(R.id.mailFragment)
                    }

                    R.id.fragmentContractorMail, R.id.fragmentTrackingProject, R.id.fragmentProjectFiles, R.id.showLocationFragment -> {
                        mNavController!!.navigateUp() || super.onSupportNavigateUp()
                    }

                    else -> {
                        super.onBackPressed()
                    }

                }
//
//                if (mNavController!!.currentDestination!!.id == R.id.mailDetailsFragment)
//
//                else if (mNavController!!.currentDestination!!.id == R.id.fragmentContractorMail||)
//                Log.d("there", "onBackPressed: "+mNavController!!.currentDestination!!.id)
//
            }
        }
    }*/


    @DelicateCoroutinesApi
    fun showProgress(show: Boolean) {
        if (progressDialog == null) {
            progressDialog = Dialog(this)
            progressDialog?.window?.setBackgroundDrawable(ColorDrawable(0))
            progressDialog?.setContentView(R.layout.layout_loading_screen)
        }
        when {
            show -> {
                if (!isFinishing && !progressDialog!!.isShowing) {
                    // to disable back button while dialog is showing
                    progressDialog?.setCancelable(false)
                    progressDialog?.setCanceledOnTouchOutside(false)
                    progressDialog?.show()
                    dialogDismissThread = GlobalScope.launch {
                        delay(30000)
                        dismissProgressDialog()
                    }
                }
            }

            else -> try {
                dismissProgressDialog()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun dismissProgressDialog() {
        if (progressDialog?.isShowing!! && !isFinishing) {
            dialogDismissThread?.cancel()
            progressDialog?.dismiss()
        }
    }

    private val listener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            when (destination.label.toString()) {

            }

        }
  var isLangChanged: Boolean = false

    override fun onDestroy() {
        super.onDestroy()
        Log.d("eee", "onDestroy: " + SharedPreferencesImpl(this).getRememberMe())

        if (!isLangChanged)
            if (SharedPreferencesImpl(this).getRememberMe() == "false")
                SharedPreferencesImpl(this).clearAll()
    }
}