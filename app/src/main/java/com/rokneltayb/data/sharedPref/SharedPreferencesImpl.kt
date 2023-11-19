package com.rokneltayb.data.sharedPref


import android.content.Context
import com.github.rtoshiro.secure.SecureSharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class SharedPreferencesImpl @Inject constructor(@ApplicationContext context: Context) :
    SharedPreferences {

    private val sharedPreferencesFileName = "SETTINGS"
    private val userApikeyToken = "USER_API_KEY_TOKEN_SHARED_PREFERENCES"
    private val refToken = "REF_TOKEN"
    private val userIdPerf = "USER_ID_SHARED_PREFERENCES"
    private val isManagerPref = "IS_MANAGER_E_IN_SHARED_PREFERENCES"
    private val userPasswordInSharedPreferences = "USER_PASSWORD_IN_SHARED_PREFERENCES"
    private val rememberMeInSharedPreferences = "REMEMBER_ME_IN_SHARED_PREFERENCES"
    private val countNotificationInSharedPreferences = "COUNT_NOTIFICAITON_IN_SHARED_PREFERENCES"
    private val languageInSharedPreferences = "LANG_IN_SHARED_PREFERENCES"


    private var prefs: SecureSharedPreferences? = SecureSharedPreferences(context,sharedPreferencesFileName)
    private val edit = prefs!!.edit()

    override fun getApiKeyToken(): String {
        return prefs!!.getString(userApikeyToken,"") ?: ""
    }

    override fun getRefToken(): String {
        return prefs!!.getString(refToken,"") ?: ""
    }


    override fun getUserPassword(): String {
        return prefs!!.getString(userPasswordInSharedPreferences,"") ?: ""
    }

    override fun getRememberMe(): String {
        return prefs!!.getString(rememberMeInSharedPreferences,"") ?:""
    }

    override fun getNotificationCount(): String {
        return prefs!!.getString(countNotificationInSharedPreferences,"") ?:""
    }


    override fun getLanguage(): String {
        return prefs!!.getString(languageInSharedPreferences,"") ?: ""
    }

    override fun setApiKeyToken(token: String) {
        edit.putString(userApikeyToken, token).apply()
    }

    override fun setRefToken(refTokenData: String) {
        edit.putString(refToken, refTokenData).apply()
    }


    override fun setUserPassword(password: String) {
        edit.putString(userPasswordInSharedPreferences, password).apply()
    }

    override fun setRememberMe(rememberMe: String) {
        edit.putString(rememberMeInSharedPreferences, rememberMe).apply()
    }

    override fun setUserId(userId: String) {
        edit.putString(userIdPerf, userId).apply()
    }

    override fun getUserId() : String {
        return prefs!!.getString(userIdPerf,"")!!
    }

    override fun setIsManager(isManager: String) {
        edit.putString(isManagerPref, isManager).apply()

    }

    override fun getIsManager() : String {
        return prefs!!.getString(isManagerPref,"") ?:""

    }

    override fun setNotificationCount(notificationCount: String) {
        edit.putString(countNotificationInSharedPreferences, notificationCount).apply()
    }



    override fun setLanguage(language: String) {
        edit.putString(languageInSharedPreferences, language).apply()
    }


    override fun clearAll() {
        edit.clear()
    }

}