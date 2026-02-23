package com.sathish.stripepaymentgateway.domain

import com.sathish.stripepaymentgateway.data.response.FetchPaymentDetailsResponse
import com.sathish.stripepaymentgateway.data.response.VerifyPaymentResponse
import retrofit2.Response

interface PaymentRepository {

    suspend fun verifyPayment(paymentIntentId: String): VerifyPaymentResponse?
    suspend fun fetchPaymentDetails(): Response<FetchPaymentDetailsResponse>?
}