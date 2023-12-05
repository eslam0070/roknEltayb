package com.rokneltayb.data.dataSource.remote.user

import javax.inject.Inject
import com.rokneltayb.data.dataSource.remote.user.UserRemoteDataSource
import com.rokneltayb.data.model.login.changepassword.ChangePasswordResponse
import com.rokneltayb.data.model.login.delete.DeleteAccountResponse
import com.rokneltayb.data.model.login.login.LoginResponse
import com.rokneltayb.data.model.login.logout.LogoutResponse
import com.rokneltayb.data.model.login.resetpassword.ResetPasswordResponse
import com.rokneltayb.data.model.signup.SignUpResponse
import com.rokneltayb.data.network.NetworkServices
import com.rokneltayb.data.network.api.RequestApiCall
import com.rokneltayb.data.network.api.RequestApiCall2
import com.rokneltayb.domain.entity.Result
import com.rokneltayb.domain.entity.Result2

class UserRemoteDataSourceImpl @Inject constructor(
    private val networkServices: NetworkServices,
    private val requestApiCall: RequestApiCall,
    private val requestApiCall2: RequestApiCall2
) : UserRemoteDataSource {
    override suspend fun login(fcmToken:String,phone: String, password: String): Result2<LoginResponse> {
        val res = requestApiCall2.requestApiCall { networkServices.login(fcmToken,phone = phone, password =  password) }

        return if (res is Result2.Success && res.data != null) {
            Result2.Success(res.data)
        } else {
            Result2.Error(res.errorType!!)
        }

    }



    override suspend fun sendOTP(phone: String): Result<SignUpResponse> {
        val res = requestApiCall.requestApiCall { networkServices.sendOTP(phone = phone) }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }

    override suspend fun resetPassword(
        countryCode: String,
        phone: String,
        token: String
    ): Result<ResetPasswordResponse> {
        val res = requestApiCall.requestApiCall { networkServices.resetPassword(countryCode,phone = phone,token) }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }

    override suspend fun changePassword(
        password: String,
        passwordConfirmation: String
    ): Result<ChangePasswordResponse> {
        val res = requestApiCall.requestApiCall { networkServices.changePassword(password,passwordConfirmation = passwordConfirmation) }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }

    override suspend fun logout(): Result<LogoutResponse> {
        val res = requestApiCall.requestApiCall { networkServices.logout() }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }

    override suspend fun deleteAccount(): Result<DeleteAccountResponse> {
        val res = requestApiCall.requestApiCall { networkServices.deleteAccount() }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }

    override suspend fun signUp(
        name: String,
        phone: String,
        email: String,
        password: String,
        passwordConfirmation: String,
        fcmToken: String
    ): Result<SignUpResponse> {
        val res = requestApiCall.requestApiCall { networkServices.signUp(name, phone, email, password, passwordConfirmation, fcmToken) }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }


}