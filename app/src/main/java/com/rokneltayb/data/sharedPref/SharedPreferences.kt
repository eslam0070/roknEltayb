package com.rokneltayb.data.sharedPref

import org.intellij.lang.annotations.Language

interface SharedPreferences {
    fun getApiKeyToken(): String
    fun getRefToken(): String
    fun getUserPassword(): String
    fun getRememberMe(): String
    fun getNotificationCount(): String
    fun getLanguage(): String
    fun setApiKeyToken(token: String)
    fun setRefToken(refTokenData: String)
    fun setUserPassword(password: String)
    fun setRememberMe(rememberMe: String)
    fun setUserId(userId: String)
    fun getUserId() : String
    fun setIsManager(isManager: String)
    fun getIsManager() : String
    fun setNotificationCount(notificationCount: String)
    fun setLanguage(language: String)

    fun clearAll()
}