package com.rokneltayb.core.base

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import com.exas.qpmoemp.core.appUtils.localization.LocalizationUtils
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
    var databinding: ActivityBaseBinding? = null

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
        databinding = DataBindingUtil.setContentView(this@BaseActivity, R.layout.activity_base)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
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

  /*  private val listener =
        NavController.OnDestinationChangedListener { _, destination, _ ->

            when (destination.label.toString()) {

                "SplashFragment", "LoginFragment", "ConfirmLoginFragment",
                "ForgetPasswordFragment", "ConfirmForgetPasswordFragment",
                "changePasswordAfterLogin", "ChangePassword" -> {
                    databinding!!.ivBack.visibility = View.GONE
                    databinding!!.ivUser.visibility = View.GONE
                    databinding!!.sideMenu.visibility = View.GONE
                    databinding!!.clMainToolbarContainer.visibility = View.GONE
                }

                "fragment_home", "MapsFragment", "HomeNewFragment" -> {
                    databinding!!.drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    databinding!!.ivBack.visibility = View.GONE
                    databinding!!.ivMainToolbarMenu.visibility = View.VISIBLE
                    databinding!!.ivUser.visibility = View.VISIBLE
                    databinding!!.tvMainEmployeeName.text = getString(R.string.home)
                    databinding!!.sideMenu.visibility = View.VISIBLE
                    databinding!!.clMainToolbarContainer.visibility = View.VISIBLE
                    if (SharedPreferencesImpl(this).getLanguage() == LANGUAGE_ARABIC)
                        databinding!!.ivUser.setImageResource(R.drawable.flag_ksa)
                    else
                        databinding!!.ivUser.setImageResource(R.drawable.flag_uk)
                    itemsNav(null)
                }

                "SettingFragment" -> {
                    databinding!!.drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    databinding!!.ivBack.visibility = View.VISIBLE
                    databinding!!.ivMainToolbarMenu.visibility = View.VISIBLE
                    databinding!!.ivUser.visibility = View.GONE
                    databinding!!.tvMainEmployeeName.text = getString(R.string.profile_setting)
                    databinding!!.sideMenu.visibility = View.VISIBLE
                    databinding!!.clMainToolbarContainer.visibility = View.VISIBLE

                    itemsNav(null)
                }

                "NoConnectionFragment" , "ServerErrorFragment" -> {
                    databinding!!.clMainToolbarContainer.visibility = View.GONE
                }

                else -> {
                    databinding!!.ivBack.visibility = View.VISIBLE
                    databinding!!.ivUser.visibility = View.GONE
                    databinding!!.ivMainToolbarMenu.visibility = View.GONE
                    databinding!!.sideMenu.visibility = View.VISIBLE
                    databinding!!.clMainToolbarContainer.visibility = View.VISIBLE
                    databinding!!.drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    itemsNav(null)
                    setTitleScreen(destination.label.toString())
                }
            }

            *//*            if (destination.label.toString().contains("SplashFragment") ||
            destination.label.toString().contains("LoginFragment") ||
            destination.label.toString().contains("ConfirmLoginFragment") ||
            destination.label.toString().contains("ForgetPasswordFragment") ||
            destination.label.toString().contains("ConfirmForgetPasswordFragment") ||
            destination.label.toString().contains("changePasswordAfterLogin") ||
            destination.label.toString().contains("ChangePassword")
        ) {
            databinding!!.ivBack.visibility = View.GONE
            databinding!!.ivUser.visibility = View.GONE
            databinding!!.sideMenu.visibility = View.GONE
            databinding!!.clMainToolbarContainer.visibility = View.GONE


        } else if (destination.label.toString()
                .contains("fragment_home") ||
            destination.label.toString()
                .contains("MapsFragment") ||
            destination.label.toString()
                .contains("HomeNewFragment")
        ) {

            databinding!!.drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            databinding!!.ivBack.visibility = View.GONE
            databinding!!.ivMainToolbarMenu.visibility = View.VISIBLE
            databinding!!.ivUser.visibility = View.VISIBLE
            databinding!!.tvMainEmployeeName.text = getString(R.string.home)
            databinding!!.sideMenu.visibility = View.VISIBLE
            databinding!!.clMainToolbarContainer.visibility = View.VISIBLE
            if (SharedPreferencesImpl(this).getLanguage() == LANGUAGE_ARABIC)
                databinding!!.ivUser.setImageResource(R.drawable.flag_ksa)
            else
                databinding!!.ivUser.setImageResource(R.drawable.flag_uk)



            itemsNav(null)


        } else if (destination.label.toString().contains("NoConnectionFragment") ||
            destination.label.toString().contains("ServerErrorFragment")
        ) {
            databinding!!.clMainToolbarContainer.visibility = View.GONE

        } else if (destination.label.toString().contains("settingsFragment")) {

            databinding!!.ivBack.visibility = View.VISIBLE
            databinding!!.ivUser.visibility = View.GONE
            databinding!!.sideMenu.visibility = View.VISIBLE
            databinding!!.tvMainEmployeeName.text = getString(R.string.profile_setting)
            databinding!!.ivMainToolbarMenu.visibility = View.VISIBLE
            databinding!!.clMainToolbarContainer.visibility = View.VISIBLE
            databinding!!.drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

            itemsNav(null)
        } else {
            databinding!!.ivBack.visibility = View.VISIBLE
            databinding!!.ivUser.visibility = View.GONE
            databinding!!.ivMainToolbarMenu.visibility = View.GONE
            databinding!!.sideMenu.visibility = View.VISIBLE
            databinding!!.clMainToolbarContainer.visibility = View.VISIBLE
            databinding!!.drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            itemsNav(null)
            setTitleScreen(destination.label.toString())
        }*//*
        }*/
  var isLangChanged: Boolean = false

    override fun onDestroy() {
        super.onDestroy()
        Log.d("eee", "onDestroy: " + SharedPreferencesImpl(this).getRememberMe())

        if (!isLangChanged)
            if (SharedPreferencesImpl(this).getRememberMe() == "false")
                SharedPreferencesImpl(this).clearAll()
    }
}