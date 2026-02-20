package com.sathish.stripepaymentgateway.core.di

import com.sathish.stripepaymentgateway.data.repository.RazorPaymentRepository
import com.sathish.stripepaymentgateway.data.repository.StripePaymentRepository
import com.sathish.stripepaymentgateway.domain.PaymentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PaymentModule {

    @Binds
    @Stripe
    abstract fun bindStripeRepo(impl: StripePaymentRepository) : PaymentRepository

    @Binds
    @Razor
    abstract fun bindRazorRepo(impl: RazorPaymentRepository) : PaymentRepository
}