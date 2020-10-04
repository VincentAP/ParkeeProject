package com.parkee.assets.extensions

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class SharePrefExtensions(
    context: Context,
    key: String
) {
    var sharePref: SharedPreferences = context.getSharedPreferences(
        key,
        Context.MODE_PRIVATE
    )

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T> get(key: String, defaultValue: T): T {
        when (T::class) {
            Boolean::class -> return sharePref.getBoolean(key, defaultValue as Boolean) as T
            Float::class -> return sharePref.getFloat(key, defaultValue as Float) as T
            Int::class -> return sharePref.getInt(key, defaultValue as Int) as T
            Long::class -> return sharePref.getLong(key, defaultValue as Long) as T
            String::class -> return sharePref.getString(key, defaultValue as String) as T
            else -> {
                if (defaultValue is Set<*>) {
                    return sharePref.getStringSet(key, defaultValue as Set<String>) as T
                }
            }
        }
        return defaultValue
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T> put(key: String, value: T) {
        val editor = sharePref.edit()

        when (T::class) {
            Boolean::class -> editor.putBoolean(key, value as Boolean)
            Float::class -> editor.putFloat(key, value as Float)
            Int::class -> editor.putInt(key, value as Int)
            Long::class -> editor.putLong(key, value as Long)
            String::class -> editor.putString(key, value as String)
            else -> {
                if (value is Set<*>) {
                    editor.putStringSet(key, value as Set<String>)
                }
            }
        }
        editor.apply()
    }

    inline fun <reified T : Any> putObject(
        key: String,
        obj: T
    ) {
        this.clear()
        val editor = sharePref.edit()
        val gson = Gson()
        val json = gson.toJson(obj)
        editor.putString(
            key,
            json
        )
        editor.apply()
    }

    inline fun <reified T : Any> getObject(key: String, clazz: Class<T>): T? {
        val gson = Gson()
        val json = sharePref.getString(key, null)
        return gson.fromJson(json, clazz)
    }

    fun clear() {
        val editor = sharePref.edit()
        editor.clear()
        editor.apply()
    }
}