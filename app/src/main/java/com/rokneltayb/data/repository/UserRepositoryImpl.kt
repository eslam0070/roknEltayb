package com.rokneltayb.data.repository


import com.rokneltayb.data.dataSource.remote.user.UserRemoteDataSource
import com.rokneltayb.data.model.login.changepassword.ChangePasswordResponse
import com.rokneltayb.data.model.login.delete.DeleteAccountResponse
import com.rokneltayb.data.model.login.login.LoginResponse
import com.rokneltayb.data.model.login.logout.LogoutResponse
import com.rokneltayb.data.model.login.profile.ProfileResponse
import com.rokneltayb.data.model.login.resetpassword.ResetPasswordResponse
import com.rokneltayb.data.model.signup.SignUpResponse
import com.rokneltayb.domain.entity.Result
import com.rokneltayb.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val remoteSource: UserRemoteDataSource): UserRepository {
    override suspend fun login(
        fcmToken: String,
        phone: String,
        password: String
    ): Result<LoginResponse> = remoteSource.login(fcmToken, phone, password)

    override suspend fun sendOTP(phone: String): Result<SignUpResponse> = remoteSource.sendOTP(phone)

    override suspend fun resetPassword(
        phone: String,
        token: String
    ): Result<ResetPasswordResponse> = remoteSource.resetPassword(phone, token)

    override suspend fun changePassword(
        password: String,
        passwordConfirmation: String
    ): Result<ChangePasswordResponse> = remoteSource.changePassword(password, passwordConfirmation)

    override suspend fun logout(): Result<LogoutResponse> = remoteSource.logout()
    override suspend fun profile(): Result<ProfileResponse> = remoteSource.profile()

    override suspend fun deleteAccount(): Result<DeleteAccountResponse> = remoteSource.deleteAccount()

    override suspend fun signUp(
        name: String,
        phone: String,
        email: String,
        password: String,
        passwordConfirmation: String,
        fcmToken: String
    ): Result<SignUpResponse> = remoteSource.signUp(name, phone, email, password, passwordConfirmation, fcmToken)


}