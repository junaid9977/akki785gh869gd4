package com.tanejkbd.dvtanejk.models

data class PaymentModel(
    private val id: String?,
    val payment_btn: String?,
    val payment_btn_type: String?,
    val payment_btn_name: String?,
    val payment_btn_logo: String?,
    val payment_btn_desc: String?,
    val payment_btn_coins: String?
)