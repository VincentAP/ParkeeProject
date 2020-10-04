package com.parkee.assets.extensions

import android.content.Context
import android.util.TypedValue
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.viewholder.LastItemDividerItem
import java.lang.reflect.Type
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat

fun Float.spToPx(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this,
        context.resources.displayMetrics
    ).toInt()
}

fun MutableList<BaseItem>.applyLastItemDecoration() {
    this.add(LastItemDividerItem())
}

fun Double.toCurrencyFormat(): String {
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    val decimalFormatSymbols: DecimalFormatSymbols = (format as DecimalFormat).decimalFormatSymbols
    decimalFormatSymbols.currencySymbol = ""
    format.decimalFormatSymbols = decimalFormatSymbols
    format.maximumFractionDigits = 0
    return format.format(this).trim()
}

inline fun <reified T> generateTypeToken(): Type? = object : TypeToken<T>() {}.type

fun BaseItem.toJson(): String? = Gson().toJson(this)

inline fun <reified T : Any> List<T>.toJson(): String? = Gson().toJson(this)

inline fun <reified T : Any> String?.fromJsonToList(): List<T>? {
    val list = this ?: "{}"
    val token = generateTypeToken<List<T>>()
    return Gson().fromJson<List<T>>(list, token)
}

inline fun <reified T : Any> String.fromJson(clazz: Class<T>): T? = Gson().fromJson(this, clazz)