package com.rokneltayb.data.dataSource.remote.settings

import com.rokneltayb.data.model.rate.AddRateResponse
import com.rokneltayb.data.model.settings.SettingsResponse
import com.rokneltayb.data.model.settings.contact.ContactUsResponse
import com.rokneltayb.domain.entity.Result
import retrofit2.Response
import retrofit2.http.Field

interface SettingsRemoteDataSource {
    suspend fun settings(): Result<SettingsResponse>
    suspend fun storeContact(name:String,phone:String,email:String,subject:String,message:String): Result<ContactUsResponse>


}