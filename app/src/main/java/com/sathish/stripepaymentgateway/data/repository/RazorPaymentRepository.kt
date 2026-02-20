package com.sathish.stripepaymentgateway.data.repository

import com.sathish.stripepaymentgateway.data.response.FetchPaymentDetailsResponse
import com.sathish.stripepaymentgateway.domain.PaymentRepository
import retrofit2.Response
import javax.inject.Inject

class RazorPaymentRepository @Inject constructor(): PaymentRepository {
    override fun orderPayment() {

    }

    override suspend fun fetchPaymentDetails(): Response<FetchPaymentDetailsResponse>? {
        return null
    }
}