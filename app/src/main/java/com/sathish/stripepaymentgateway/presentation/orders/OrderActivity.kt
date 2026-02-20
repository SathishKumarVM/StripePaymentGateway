package com.sathish.stripepaymentgateway.presentation.orders

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sathish.stripepaymentgateway.presentation.orders.viewmodel.OrderViewModel
import com.sathish.stripepaymentgateway.presentation.ui.theme.StripePaymentGatewayTheme
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheet.Builder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderActivity : ComponentActivity() {
    private lateinit var paymentSheet: PaymentSheet
    private val viewModel: OrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        paymentSheet = Builder { result ->
            viewModel.onPaymentResult(result)
        }.build(this)

        enableEdgeToEdge()
        setContent {
            StripePaymentGatewayTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    OrderScreen(
                        paymentSheet = paymentSheet,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun OrderScreen(
    paymentSheet: PaymentSheet,
    modifier: Modifier = Modifier,
    viewModel: OrderViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchPaymentDetails()
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (uiState.isLoading) {
            CircularProgressIndicator()
        }

        Button(
            onClick = {
                val customerConfig = viewModel.customerConfig
                val paymentIntent = viewModel.paymentIntent

                if (customerConfig != null && paymentIntent != null) {
                    paymentSheet.presentWithPaymentIntent(
                        paymentIntent,
                        PaymentSheet.Configuration(
                            merchantDisplayName = "My Merchant Name",
                            customer = customerConfig
                        )
                    )
                }
            },
            enabled = uiState.isReady
        ) {
            Text("Pay Now")
        }

        uiState.error?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = it, color = Color.Red)
        }
    }
}