package com.rokneltayb.domain.usecase

import com.rokneltayb.data.model.settings.SettingsResponse
import com.rokneltayb.data.model.settings.contact.ContactUsResponse
import com.rokneltayb.data.model.settings.pages.PagesResponse
import com.rokneltayb.domain.entity.Result
import com.rokneltayb.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsUseCase @Inject constructor(private val repo: SettingsRepository) {
    suspend fun settings(): Result<SettingsResponse> = repo.settings()
    suspend fun storeContact(name:String, phone:String, email:String, subject:String, message:String): Result<ContactUsResponse> = repo.storeContact(name, phone, email, subject, message)
    suspend fun getPages(): Result<PagesResponse> = repo.getPages()

}