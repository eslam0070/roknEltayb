package com.rokneltayb.data.sharedPref

import com.rokneltayb.data.model.login.login.ClientData
import org.intellij.lang.annotations.Language

interface SharedPreferences {
    fun getApiKeyToken(): String
    fun getRefToken(): String
    fun getUserPassword(): String
    fun getRememberMe(): Boolean
    fun getNotificationCount(): String
    fun getLanguage(): String
    fun getUserProfil(): ClientData?
    fun setApiKeyToken(token: String)
    fun setRefToken(refTokenData: String)
    fun setUserPassword(password: String)
    fun setRememberMe(rememberMe: Boolean)
    fun setUserId(userId: String)
    fun getUserId() : String
    fun setIsManager(isManager: String)

    fun setUserProfil(userProfile:ClientData)
    fun getIsManager() : String
    fun setNotificationCount(notificationCount: String)
    fun setLanguage(language: String)

    fun clearAll()
}