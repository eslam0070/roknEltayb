package com.rokneltayb.data.dataSource.remote.home

import com.rokneltayb.data.model.home.home.HomeResponse
import javax.inject.Inject
import com.rokneltayb.data.model.login.changepassword.ChangePasswordResponse
import com.rokneltayb.data.model.login.delete.DeleteAccountResponse
import com.rokneltayb.data.model.login.login.LoginResponse
import com.rokneltayb.data.model.login.logout.LogoutResponse
import com.rokneltayb.data.model.login.resetpassword.ResetPasswordResponse
import com.rokneltayb.data.model.signup.SignUpResponse
import com.rokneltayb.data.network.NetworkServices
import com.rokneltayb.data.network.api.RequestApiCall
import com.rokneltayb.domain.entity.Result

class HomeRemoteDataSourceImpl @Inject constructor(
    private val networkServices: NetworkServices,
    private val requestApiCall: RequestApiCall
) : HomeRemoteDataSource {
    override suspend fun home(): Result<HomeResponse> {
        val res = requestApiCall.requestApiCall { networkServices.home() }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }


}