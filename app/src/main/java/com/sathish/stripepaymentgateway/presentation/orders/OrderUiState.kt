package com.sathish.stripepaymentgateway.presentation.orders

data class OrderUiState(
    val isLoading: Boolean = false,
    val isReady: Boolean = false,
    val isPaymentSuccess: Boolean = false,
    val error: String? = null
)