package com.sathish.stripepaymentgateway.domain

import com.sathish.stripepaymentgateway.data.response.FetchPaymentDetailsResponse
import retrofit2.Response

interface PaymentRepository {

    fun orderPayment()
    suspend fun fetchPaymentDetails(): Response<FetchPaymentDetailsResponse>?
}