package com.sathish.stripepaymentgateway.presentation.orders.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sathish.stripepaymentgateway.domain.OrderUseCase
import com.sathish.stripepaymentgateway.presentation.orders.OrderUiState
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val orderUseCase: OrderUseCase
): ViewModel() {

    var customerId: String? = null
    var paymentIntent: String? = null
    var paymentIntentId: String? = null
    var publishableKey: String? = null
    var customerSessionClientSecret: String? = null

    var customerConfig: PaymentSheet.CustomerConfiguration? = null

    private val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    fun fetchPaymentDetails() {
        viewModelScope.launch {
            _uiState.value = OrderUiState(isLoading = true)

            try {
                val response = withContext(Dispatchers.IO) {
                    orderUseCase.fetchPaymentDetail()
                }

                if (response?.isSuccessful == true && response.body() != null) {

                    val body = response.body()!!

                    customerId = body.customer
                    paymentIntent = body.paymentIntent
                    paymentIntentId = body.paymentIntentId
                    publishableKey = body.publishableKey
                    customerSessionClientSecret = body.customerSessionClientSecret

                    if (customerId != null && customerSessionClientSecret != null) {
                        customerConfig =
                            PaymentSheet.CustomerConfiguration.createWithCustomerSession(
                                id = customerId!!,
                                clientSecret = customerSessionClientSecret!!
                            )
                    }

                    publishableKey?.let {
                        PaymentConfiguration.init(context, it)
                    }

                    _uiState.value = OrderUiState(isReady = true)

                } else {
                    _uiState.value = OrderUiState(error = "Payment fetch failed")
                }

            } catch (e: Exception) {
                _uiState.value = OrderUiState(error = e.message)
            }
        }
    }

    fun onPaymentResult(result: PaymentSheetResult) {
        when (result) {
            is PaymentSheetResult.Completed -> {
                // Ideally verify payment with backend here or web hooks
                _uiState.value = OrderUiState()
                verifyPaymentFromBackend()
            }
            is PaymentSheetResult.Failed -> {
                _uiState.value =
                    OrderUiState(error = result.error.message)
            }
            is PaymentSheetResult.Canceled -> {
                _uiState.value = OrderUiState()
            }
        }
    }

    fun verifyPaymentFromBackend() {

        val intentId = paymentIntentId ?: return

        viewModelScope.launch {
            _uiState.value = OrderUiState(isLoading = true)

            try {
                val result = withContext(Dispatchers.IO) {
                    orderUseCase.verifyPayment(intentId)
                }
                if (result?.status == "paid") {
                    _uiState.value = OrderUiState(isPaymentSuccess = true)
                } else {
                    _uiState.value =
                        OrderUiState(error = "Payment not completed yet")
                }
            } catch (e: Exception) {
                _uiState.value = OrderUiState(error = e.message)
            }
        }
    }
}