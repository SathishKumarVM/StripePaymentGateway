package com.sathish.stripepaymentgateway.domain

import com.sathish.stripepaymentgateway.core.di.Razor
import com.sathish.stripepaymentgateway.core.di.Stripe
import com.sathish.stripepaymentgateway.data.response.FetchPaymentDetailsResponse
import com.sathish.stripepaymentgateway.data.response.VerifyPaymentResponse
import retrofit2.Response
import javax.inject.Inject

class OrderUseCase @Inject constructor(
    @Stripe private val stripePaymentRepository: PaymentRepository,
    @Razor private val razorPaymentRepository: PaymentRepository
) {
    suspend fun verifyPayment(paymentIntentId: String): VerifyPaymentResponse? =
        stripePaymentRepository.verifyPayment(paymentIntentId)

    suspend fun fetchPaymentDetail(): Response<FetchPaymentDetailsResponse>? {
        return stripePaymentRepository.fetchPaymentDetails()
    }
}