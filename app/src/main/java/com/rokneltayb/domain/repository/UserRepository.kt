package com.rokneltayb.domain.repository

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
import com.rokneltayb.domain.entity.Result

interface UserRepository {
    suspend fun login(fcmToken:String,phone: String, password: String): Result<LoginResponse>
    suspend fun sendOTP(phone: String): Result<SignUpResponse>
    suspend fun resetPassword(phone: String,token: String): Result<ResetPasswordResponse>
    suspend fun changePassword(oldPassword:String,password:String,passwordConfirmation: String): Result<ChangePasswordResponse>

    suspend fun logout(): Result<LogoutResponse>
    suspend fun profile(): Result<ProfileResponse>
    suspend fun updateProfile(name:String,phone:String,email:String): Result<UpdateProfileResponse>
    suspend fun deleteAccount(): Result<DeleteAccountResponse>
    suspend fun signUp(name:String,phone: String,email: String, password: String,passwordConfirmation: String,fcmToken: String): Result<SignUpResponse>
    suspend fun verifyPhone(phone: String,token: String): Result<OtpSignUpResponse>
    suspend fun resendVerifyPhone(phone: String): Result<ResendOtpSignUpResponse>
}