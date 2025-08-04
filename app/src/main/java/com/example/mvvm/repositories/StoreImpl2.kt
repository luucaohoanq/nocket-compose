package com.example.mvvm.repositories

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StoreImpl2 @Inject constructor(@ApplicationContext context: Context) : Store {
    private val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    override fun getValue(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }

    override fun setValue(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }
}