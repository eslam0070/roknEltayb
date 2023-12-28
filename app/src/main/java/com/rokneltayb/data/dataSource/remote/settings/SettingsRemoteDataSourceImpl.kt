package com.rokneltayb.data.dataSource.remote.settings

import javax.inject.Inject
import com.rokneltayb.data.model.rate.AddRateResponse
import com.rokneltayb.data.model.settings.SettingsResponse
import com.rokneltayb.data.model.settings.contact.ContactUsResponse
import com.rokneltayb.data.network.NetworkServices
import com.rokneltayb.data.network.api.RequestApiCall
import com.rokneltayb.domain.entity.Result

class SettingsRemoteDataSourceImpl @Inject constructor(
    private val networkServices: NetworkServices,
    private val requestApiCall: RequestApiCall
) : SettingsRemoteDataSource {
    override suspend fun settings(): Result<SettingsResponse> {
        val res = requestApiCall.requestApiCall { networkServices.settings() }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }

    override suspend fun storeContact(
        name: String,
        phone: String,
        email: String,
        subject: String,
        message: String
    ): Result<ContactUsResponse> {
        val res = requestApiCall.requestApiCall { networkServices.storeContact(name, phone, email, subject, message) }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }
}