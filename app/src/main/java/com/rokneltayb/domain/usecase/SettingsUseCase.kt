package com.rokneltayb.domain.usecase

import com.rokneltayb.data.model.cart.CartResponse
import com.rokneltayb.data.model.cart.add.AddCartResponse
import com.rokneltayb.data.model.cart.delete.DeleteCartResponse
import com.rokneltayb.data.model.categories.CategoriesResponse
import com.rokneltayb.data.model.home.home.HomeResponse
import com.rokneltayb.data.model.login.changepassword.ChangePasswordResponse
import com.rokneltayb.data.model.login.delete.DeleteAccountResponse
import com.rokneltayb.data.model.login.login.LoginResponse
import com.rokneltayb.data.model.login.logout.LogoutResponse
import com.rokneltayb.data.model.login.resetpassword.ResetPasswordResponse
import com.rokneltayb.data.model.products.ProductsResponse
import com.rokneltayb.data.model.rate.AddRateResponse
import com.rokneltayb.data.model.settings.SettingsResponse
import com.rokneltayb.data.model.settings.contact.ContactUsResponse
import com.rokneltayb.data.model.settings.pages.PagesResponse
import com.rokneltayb.data.model.signup.SignUpResponse
import com.rokneltayb.domain.entity.Result
import com.rokneltayb.domain.repository.CartRepository
import com.rokneltayb.domain.repository.HomeRepository
import com.rokneltayb.domain.repository.RateRepository
import com.rokneltayb.domain.repository.SettingsRepository
import com.rokneltayb.domain.repository.UserRepository
import javax.inject.Inject

class SettingsUseCase @Inject constructor(private val repo: SettingsRepository) {
    suspend fun settings(): Result<SettingsResponse> = repo.settings()
    suspend fun storeContact(name:String, phone:String, email:String, subject:String, message:String): Result<ContactUsResponse> = repo.storeContact(name, phone, email, subject, message)
    suspend fun getPages(): Result<PagesResponse> = repo.getPages()

}