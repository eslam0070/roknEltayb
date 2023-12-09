package com.rokneltayb.data.dataSource.remote.home

import com.rokneltayb.data.model.home.home.HomeResponse
import com.rokneltayb.data.model.login.login.LoginResponse
import com.rokneltayb.domain.entity.Result

interface HomeRemoteDataSource {

    suspend fun home(): Result<HomeResponse>


}