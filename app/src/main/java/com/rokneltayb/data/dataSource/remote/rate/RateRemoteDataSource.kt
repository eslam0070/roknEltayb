package com.rokneltayb.data.dataSource.remote.rate

import com.rokneltayb.data.model.rate.AddRateResponse
import com.rokneltayb.domain.entity.Result

interface RateRemoteDataSource {
    suspend fun storeRate(userId:Int,productId:Int,rate:String,description:String): Result<AddRateResponse>

}