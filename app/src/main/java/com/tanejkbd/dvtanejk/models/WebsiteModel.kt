package com.tanejkbd.dvtanejk.models

import java.io.Serializable

data class WebsiteModel(
    val id: String?,
    val is_visit_enable: String?,
    val visit_title: String?,
    val visit_link: String?,
    val visit_coin: String?,
    val visit_timer: String?,
    val browser: String?,
    val description: String?,
    val logo: String?,
    val packages: String?,
): Serializable