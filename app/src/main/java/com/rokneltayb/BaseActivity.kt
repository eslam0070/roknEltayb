package com.rokneltayb

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation
import com.rokneltayb.data.sharedPref.SharedPreferencesImpl
import com.rokneltayb.databinding.ActivityBaseBinding
import com.rokneltayb.domain.util.Constants
import com.rokneltayb.domain.util.Constants.LANGUAGE_ARABIC
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.localization.LocalizationUtils
import com.rokneltayb.domain.util.logoutNoAuth
import com.rokneltayb.domain.util.toast
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.more.favorite.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BaseActivity : AppCompatActivity() {

    private var progressDialog: Dialog? = null
    var dialogDismissThread: Job? = null
    var binding: ActivityBaseBinding? = null
    private val mNavController by lazy { Navigation.findNavController(this, R.id.navHostFragment) }
    val sharedPref by lazy { SharedPreferencesImpl(this) }
    private val viewModel: NotificationCountViewModel by viewModels()

    private fun observeUIState() {
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }

    }

    private fun updateUI(uiState: NotificationCountViewModel.UiState) {
        when (uiState) {
            is NotificationCountViewModel.UiState.Loading -> {
            }

            is NotificationCountViewModel.UiState.Error -> {
            }

            is NotificationCountViewModel.UiState.Success -> {
                if (uiState.data.data!!.count != 0)
                    binding!!.notificationCountTextView.text = uiState.data.data!!.count.toString()
                else
                    binding!!.notificationCountTextView.visibility = View.GONE

            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeUIState()
        if (Build.VERSION.SDK_INT >= 31) {
            window.decorView.layoutDirection = resources.configuration.layoutDirection
        }

        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this@BaseActivity, R.layout.activity_base)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val bottomNavigationItems = mutableListOf(
            CurvedBottomNavigation.Model(R.id.homeFragment, getString(R.string.home),R.drawable.ic_home_gray),
            CurvedBottomNavigation.Model(R.id.categoriesFragment, getString(R.string.categories),R.drawable.ic_categories_gray),
            CurvedBottomNavigation.Model(R.id.cartFragment, getString(R.string.cart),R.drawable.ic_cart_gray),
            CurvedBottomNavigation.Model(R.id.moreFragment, getString(R.string.more),R.drawable.ic_more))

        viewModel.notificationCount()
        binding!!.ivBack.setOnClickListener {
            findNavController(R.id.navHostFragment).navigateUp()
        }

        binding!!.bottomNavigation.apply {
            bottomNavigationItems.forEach{
                add(it)
            }
            setOnClickMenuListener {
                mNavController.navigate(it.id)
            }
            setupNavController(mNavController)
        }

        binding!!.ivFav.setOnClickListener {
            if (sharedPref.getRememberMe())
                findNavController(R.id.navHostFragment).navigate(R.id.favoritesFragment)
            else
                toast(getString(R.string.you_should_login))
        }

        binding!!.ivSearch.setOnClickListener {
            findNavController(R.id.navHostFragment).navigate(R.id.searchHomeFragment)
        }
    }


   override fun onBackPressed() {
       if (mNavController.currentDestination!!.id == R.id.homeFragment)
           super.onBackPressed()
       else {
           when (mNavController.currentDestination!!.id) {
               R.id.categoriesFragment -> mNavController.popBackStack(R.id.homeFragment, false)
               R.id.cartFragment -> mNavController.popBackStack(R.id.homeFragment, false)
               R.id.moreFragment -> mNavController.popBackStack(R.id.homeFragment, false)
               else -> mNavController.navigateUp()
           }
       }
   }
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
            }catch (i: IllegalArgumentException){
                i.printStackTrace()
            }
        }
    }

    private fun dismissProgressDialog() {
        if (progressDialog != null){
            if (progressDialog?.isShowing!! && !isFinishing) {
                dialogDismissThread?.cancel()
                progressDialog?.dismiss()
            }
        }
    }

    private val listener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            when (destination.label.toString()) {
                "SplashFragment","fragment_register","LoginFragment","ForgetPasswordFragment",
                "ResetYourPasswordFragment","VerifyYourAccountFragment"->{
                    binding!!.bottomNavigation.visibility = View.GONE
                    binding!!.clMainToolbarContainer.visibility = View.GONE
                }
                "fragment_home"->{
                    binding!!.clMainToolbarContainer.visibility = View.VISIBLE
                    binding!!.bottomNavigation.visibility = View.VISIBLE
                    binding!!.ivBack.visibility = View.GONE
                    binding!!.ivHome.visibility = View.VISIBLE
                    binding!!.tvMainEmployeeName.text = ""
                    binding!!.ivFav.visibility = View.VISIBLE
                    binding!!.ivSearch.visibility = View.GONE

                }
                "fragment_more" ->{
                    binding!!.clMainToolbarContainer.visibility = View.VISIBLE
                    binding!!.bottomNavigation.visibility = View.VISIBLE
                    binding!!.tvMainEmployeeName.text = getString(R.string.more)
                    binding!!.ivBack.visibility = View.GONE
                    binding!!.ivHome.visibility = View.GONE
                    binding!!.ivFav.visibility = View.GONE
                    binding!!.notificationCountTextView.visibility = View.GONE
                    binding!!.ivSearch.visibility = View.GONE
                }
                "fragment_categories"->{
                    binding!!.clMainToolbarContainer.visibility = View.VISIBLE
                    binding!!.tvMainEmployeeName.text = getString(R.string.categories)
                    binding!!.bottomNavigation.visibility = View.VISIBLE
                    binding!!.ivBack.visibility = View.GONE
                    binding!!.ivHome.visibility = View.GONE
                    binding!!.ivFav.visibility = View.VISIBLE
                    binding!!.notificationCountTextView.visibility = View.VISIBLE

                    binding!!.ivSearch.visibility = View.GONE
                } "fragment_products"->{
                    binding!!.clMainToolbarContainer.visibility = View.VISIBLE
                    binding!!.bottomNavigation.visibility = View.GONE
                    binding!!.ivBack.visibility = View.GONE
                    binding!!.ivHome.visibility = View.GONE
                    binding!!.ivFav.visibility = View.VISIBLE
                    binding!!.notificationCountTextView.visibility = View.GONE
                binding!!.ivSearch.visibility = View.GONE
                }
                "fragment_search" ->{
                    binding!!.bottomNavigation.visibility = View.GONE
                    binding!!.clMainToolbarContainer.visibility = View.GONE
                    binding!!.ivBack.visibility = View.GONE
                    binding!!.ivFav.visibility = View.GONE
                    binding!!.ivHome.visibility = View.GONE
                    binding!!.ivSearch.visibility = View.GONE
                    binding!!.notificationCountTextView.visibility = View.GONE

                }

                "fragment_new_address" -> {
                    binding!!.bottomNavigation.visibility = View.GONE
                    binding!!.clMainToolbarContainer.visibility = View.VISIBLE
                    binding!!.tvMainEmployeeName.text = getString(R.string.new_address)
                    binding!!.ivBack.visibility = View.VISIBLE
                    binding!!.ivFav.visibility = View.GONE
                    binding!!.ivHome.visibility = View.GONE
                    binding!!.ivSearch.visibility = View.GONE
                    binding!!.notificationCountTextView.visibility = View.GONE

                }"fragment_address" -> {
                    binding!!.bottomNavigation.visibility = View.GONE
                    binding!!.clMainToolbarContainer.visibility = View.VISIBLE
                    binding!!.tvMainEmployeeName.text = getString(R.string.address2)
                    binding!!.ivBack.visibility = View.VISIBLE
                    binding!!.ivFav.visibility = View.GONE
                    binding!!.ivHome.visibility = View.GONE
                    binding!!.ivSearch.visibility = View.GONE
                binding!!.notificationCountTextView.visibility = View.GONE

            }"fragment_contact_us" -> {
                    binding!!.bottomNavigation.visibility = View.GONE
                    binding!!.clMainToolbarContainer.visibility = View.VISIBLE
                    binding!!.tvMainEmployeeName.text = getString(R.string.contact_us)
                    binding!!.ivBack.visibility = View.VISIBLE
                    binding!!.ivFav.visibility = View.GONE
                    binding!!.ivHome.visibility = View.GONE
                    binding!!.ivSearch.visibility = View.GONE
                binding!!.notificationCountTextView.visibility = View.GONE

            }"fragment_privacy_policy" -> {
                    binding!!.bottomNavigation.visibility = View.GONE
                    binding!!.clMainToolbarContainer.visibility = View.VISIBLE
                    binding!!.tvMainEmployeeName.text = getString(R.string.privacy_policy)
                    binding!!.ivBack.visibility = View.VISIBLE
                    binding!!.ivFav.visibility = View.GONE
                    binding!!.ivHome.visibility = View.GONE
                    binding!!.ivSearch.visibility = View.GONE
                binding!!.notificationCountTextView.visibility = View.GONE
            }"fragment_popular_products","fragment_daily_products" ->{
                    binding!!.bottomNavigation.visibility = View.GONE
                    binding!!.clMainToolbarContainer.visibility = View.VISIBLE
                    binding!!.ivBack.visibility = View.VISIBLE
                    binding!!.ivFav.visibility = View.VISIBLE
                    binding!!.ivHome.visibility = View.GONE
                    binding!!.ivSearch.visibility = View.VISIBLE
                binding!!.notificationCountTextView.visibility = View.VISIBLE

            }"fragment_cart" ->{
                    binding!!.bottomNavigation.visibility = View.VISIBLE
                    binding!!.clMainToolbarContainer.visibility = View.VISIBLE
                    binding!!.ivBack.visibility = View.GONE
                    binding!!.ivFav.visibility = View.VISIBLE
                    binding!!.ivHome.visibility = View.GONE
                    binding!!.ivSearch.visibility = View.GONE
                binding!!.notificationCountTextView.visibility = View.VISIBLE

                }"fragment_check_out" ->{
                    binding!!.bottomNavigation.visibility = View.GONE
                    binding!!.clMainToolbarContainer.visibility = View.VISIBLE
                    binding!!.ivBack.visibility = View.VISIBLE
                    binding!!.ivFav.visibility = View.VISIBLE
                    binding!!.ivHome.visibility = View.GONE
                    binding!!.ivSearch.visibility = View.GONE
                binding!!.notificationCountTextView.visibility = View.VISIBLE


            } "fragment_order" ->{
                    binding!!.bottomNavigation.visibility = View.GONE
                    binding!!.clMainToolbarContainer.visibility = View.VISIBLE
                    binding!!.ivBack.visibility = View.VISIBLE
                    binding!!.ivFav.visibility = View.GONE
                    binding!!.ivHome.visibility = View.GONE
                    binding!!.ivSearch.visibility = View.GONE
                binding!!.notificationCountTextView.visibility = View.GONE

                } else ->{
                    binding!!.bottomNavigation.visibility = View.GONE
                    binding!!.clMainToolbarContainer.visibility = View.VISIBLE
                    binding!!.ivBack.visibility = View.VISIBLE
                    binding!!.ivFav.visibility = View.GONE
                    binding!!.ivHome.visibility = View.GONE
                    binding!!.ivSearch.visibility = View.GONE
                binding!!.notificationCountTextView.visibility = View.GONE

                }
            }

        }
  var isLangChanged: Boolean = false

    override fun onDestroy() {
        super.onDestroy()
        Log.d("eee", "onDestroy: " + SharedPreferencesImpl(this).getRememberMe())
            if(progressDialog != null){
                progressDialog?.dismiss();
                progressDialog = null;
            }

        if (!isLangChanged)
            if (!SharedPreferencesImpl(this).getRememberMe())
                SharedPreferencesImpl(this).clearAll()
    }

    override fun onStart() {
        super.onStart()
        if (sharedPref.getLanguage().isEmpty() ||
            sharedPref.getLanguage() == LANGUAGE_ARABIC) {
            sharedPref.setLanguage(LANGUAGE_ARABIC)
            binding!!.ivHome.setImageResource(R.drawable.logo_home_ar)
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ar"))
            binding!!.root.layoutDirection = View.LAYOUT_DIRECTION_RTL
        } else {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
            SharedPreferencesImpl(this).setLanguage(Constants.LANGUAGE_ENGLISH)
            binding!!.ivHome.setImageResource(R.drawable.logo_home_en)
            binding!!.root.layoutDirection = View.LAYOUT_DIRECTION_LTR
        }
        mNavController.addOnDestinationChangedListener(listener)
    }
    override fun onResume() {
        super.onResume()

        mNavController.addOnDestinationChangedListener(listener)
    }
    override fun onPause() {
        super.onPause()
        mNavController.removeOnDestinationChangedListener(listener)
    }
}