package com.rokneltayb.data.sharedPref


import android.content.Context
import android.os.Build
import com.github.rtoshiro.secure.SecureSharedPreferences
import com.google.gson.Gson
import com.rokneltayb.data.model.login.login.ClientData
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
    private val userProfileInSharedPreferences = "USER_PROFILE_IN_SHARED_PREFERENCES"


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

    override fun getRememberMe(): Boolean {
        return prefs!!.getBoolean(rememberMeInSharedPreferences,false)
    }

    override fun getNotificationCount(): String {
        return prefs!!.getString(countNotificationInSharedPreferences,"") ?:""
    }


    override fun getLanguage(): String {
        return prefs!!.getString(languageInSharedPreferences,"") ?: ""
    }

    override fun getUserProfil(): ClientData? {
        val gson = Gson()
        val json = prefs!!.getString(userProfileInSharedPreferences,"") ?: ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (json == "") return null
        }
        return gson.fromJson(json, ClientData::class.java)
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

    override fun setRememberMe(rememberMe: Boolean) {
        edit.putBoolean(rememberMeInSharedPreferences, rememberMe).apply()
    }

    override fun setUserId(userId: Int) {
        edit.putInt(userIdPerf, userId).apply()
    }

    override fun getUserId() : Int {
        return prefs!!.getInt(userIdPerf,0)
    }

    override fun setIsManager(isManager: String) {
        edit.putString(isManagerPref, isManager).apply()

    }

    override fun setUserProfil(userProfile: ClientData) {
        val gson = Gson()
        val json = gson.toJson(userProfile)
        edit.putString(userProfileInSharedPreferences, json).apply()
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