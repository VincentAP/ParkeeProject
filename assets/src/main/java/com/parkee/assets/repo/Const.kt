package com.parkee.assets.repo

import java.text.SimpleDateFormat
import java.util.*

object Const {
    const val BASE_URL = "https://api.sandbox.transferwise.tech/"
    const val HEADER_ATTR = "Bearer 14debfdc-84db-4e44-8684-f63473b2ae0f"
    val CREATED_DATE_PATTERN = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
    val DD_MMMM_YYYY_DATE_PATTERN = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
    val YYYY_MM_DD_HH_MM_SS_DATE_PATTERN = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    val DD_MMMM_YYYY_HH_MM_SS_DATE_PATTERN = SimpleDateFormat("dd MMMM yyyy HH:mm:ss", Locale.ENGLISH)
    val MMMM_D_TH_DATE_PATTERN = SimpleDateFormat("MMMM d'th'", Locale.ENGLISH)
    val RADIO_BUTTON_KEY = "RADIO_BUTTON_KEY"
    val CURRENT_SELECTED_RADIO_BUTTON_KEY = "CURRENT_SELECTED_RADIO_BUTTON_KEY"
}