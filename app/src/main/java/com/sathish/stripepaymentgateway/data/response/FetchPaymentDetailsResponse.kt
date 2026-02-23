package com.sathish.stripepaymentgateway.data.response

data class FetchPaymentDetailsResponse(
    val publishableKey: String,
    val customerSessionClientSecret: String,
    val paymentIntent: String,
    val customer: String,
    val paymentIntentId: String,
)