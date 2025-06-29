package com.rokneltayb.domain.usecase

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
import com.rokneltayb.domain.repository.UserRepository
import javax.inject.Inject

class UserUseCase @Inject constructor(private val repo: UserRepository) {
    suspend fun login(
        fcmToken: String,
        phone: String,
        password: String
    ): Result<LoginResponse> = repo.login(fcmToken, phone, password)

    suspend fun sendOTP(phone: String): Result<SignUpResponse> = repo.sendOTP(phone)

    suspend fun resetPassword(
        phone: String,
        token: String
    ): Result<ResetPasswordResponse> = repo.resetPassword(phone, token)

    suspend fun changePassword(
        oldPassword: String,
        password: String,
        passwordConfirmation: String
    ): Result<ChangePasswordResponse> = repo.changePassword(oldPassword,password, passwordConfirmation)

    suspend fun logout(): Result<LogoutResponse> = repo.logout()
    suspend fun profile(): Result<ProfileResponse> = repo.profile()
    suspend fun updateProfile(
        name: String,
        phone: String,
        email: String
    ): Result<UpdateProfileResponse> = repo.updateProfile(name, phone, email)
    suspend fun deleteAccount(): Result<DeleteAccountResponse> = repo.deleteAccount()

    suspend fun signUp(
        name: String,
        phone: String,
        email: String,
        password: String,
        passwordConfirmation: String,
        fcmToken: String
    ): Result<SignUpResponse> = repo.signUp(name, phone, email, password, passwordConfirmation, fcmToken)

    suspend fun verifyPhone(phone: String,token: String): Result<OtpSignUpResponse>
            = repo.verifyPhone(phone, token)
    suspend fun resendVerifyPhone(phone: String): Result<ResendOtpSignUpResponse>
            = repo.resendVerifyPhone(phone)
}