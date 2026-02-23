package com.sathish.stripepaymentgateway.data.remote

import com.sathish.stripepaymentgateway.data.response.FetchPaymentDetailsResponse
import com.sathish.stripepaymentgateway.data.response.VerifyPaymentResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("cart-session.php")
    suspend fun fetchPaymentDetails(): Response<FetchPaymentDetailsResponse>

    @GET("verify_payment.php")
    suspend fun verifyPayment(
        @Query("payment_intent_id") paymentIntentId: String
    ): Response<VerifyPaymentResponse>
}