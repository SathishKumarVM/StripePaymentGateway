package com.sathish.stripepaymentgateway.data.remote

import com.sathish.stripepaymentgateway.data.response.FetchPaymentDetailsResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("cart-session.php")
    suspend fun fetchPaymentDetails(): Response<FetchPaymentDetailsResponse>
}