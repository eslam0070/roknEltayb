package com.rokneltayb.data.dataSource.remote.user

import javax.inject.Inject
import com.rokneltayb.data.model.login.changepassword.ChangePasswordResponse
import com.rokneltayb.data.model.login.delete.DeleteAccountResponse
import com.rokneltayb.data.model.login.login.LoginResponse
import com.rokneltayb.data.model.login.logout.LogoutResponse
import com.rokneltayb.data.model.login.profile.ProfileResponse
import com.rokneltayb.data.model.login.profile.update.UpdateProfileResponse
import com.rokneltayb.data.model.login.resetpassword.ResetPasswordResponse
import com.rokneltayb.data.model.otp.OtpSignUpResponse
import com.rokneltayb.data.model.otp.ResendOtpSignUpResponse
import com.rokneltayb.data.model.signup.SignUpResponse
import com.rokneltayb.data.network.NetworkServices
import com.rokneltayb.data.network.api.RequestApiCall
import com.rokneltayb.domain.entity.Result

class UserRemoteDataSourceImpl @Inject constructor(
    private val networkServices: NetworkServices,
    private val requestApiCall: RequestApiCall
) : UserRemoteDataSource {
    override suspend fun login(fcmToken:String,phone: String, password: String): Result<LoginResponse> {
        val res = requestApiCall.requestApiCall { networkServices.login(fcmToken,phone = phone, password =  password) }

        return if (res is Result.Success && res.data != null) {
            Result.Success(res.data)
        } else {
            Result.Error(res.errorType)
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
        phone: String,
        token: String
    ): Result<ResetPasswordResponse> {
        val res = requestApiCall.requestApiCall { networkServices.resetPassword(phone = phone,token) }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }


    override suspend fun changePassword(
        oldPassword: String,
        password: String,
        passwordConfirmation: String
    ): Result<ChangePasswordResponse> {
        val res = requestApiCall.requestApiCall { networkServices.changePassword(oldPassword,password,passwordConfirmation = passwordConfirmation) }

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

    override suspend fun profile(): Result<ProfileResponse> {
        val res = requestApiCall.requestApiCall { networkServices.profile() }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }

    override suspend fun updateProfile(
        name: String,
        phone: String,
        email: String
    ): Result<UpdateProfileResponse> {
        val res = requestApiCall.requestApiCall { networkServices.updateProfile(name, phone, email) }

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
    } override suspend fun verifyPhone(
        phone: String,
        token: String,
    ): Result<OtpSignUpResponse> {
        val res = requestApiCall.requestApiCall { networkServices.verifyPhone(phone, token) }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    } override suspend fun resendVerifyPhone(
        phone: String
    ): Result<ResendOtpSignUpResponse> {
        val res = requestApiCall.requestApiCall { networkServices.resendVerifyPhone(phone) }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }


}