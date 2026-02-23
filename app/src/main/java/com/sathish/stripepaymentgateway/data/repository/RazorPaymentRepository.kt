package com.sathish.stripepaymentgateway.data.repository

import com.sathish.stripepaymentgateway.data.response.FetchPaymentDetailsResponse
import com.sathish.stripepaymentgateway.data.response.VerifyPaymentResponse
import com.sathish.stripepaymentgateway.domain.PaymentRepository
import retrofit2.Response
import javax.inject.Inject

class RazorPaymentRepository @Inject constructor(): PaymentRepository {

    override suspend fun verifyPayment(paymentIntentId: String): VerifyPaymentResponse? = null

    override suspend fun fetchPaymentDetails(): Response<FetchPaymentDetailsResponse>? = null
}