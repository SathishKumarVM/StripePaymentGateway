package com.sathish.stripepaymentgateway.data.repository

import com.sathish.stripepaymentgateway.data.remote.ApiService
import com.sathish.stripepaymentgateway.data.response.FetchPaymentDetailsResponse
import com.sathish.stripepaymentgateway.data.response.VerifyPaymentResponse
import com.sathish.stripepaymentgateway.domain.PaymentRepository
import retrofit2.Response
import javax.inject.Inject

class StripePaymentRepository @Inject constructor(private val apiService: ApiService): PaymentRepository {

    override suspend fun verifyPayment(paymentIntentId: String): VerifyPaymentResponse? {

        val response = apiService.verifyPayment(paymentIntentId)

        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    override suspend fun fetchPaymentDetails(): Response<FetchPaymentDetailsResponse>? {
        return apiService.fetchPaymentDetails()
    }
}