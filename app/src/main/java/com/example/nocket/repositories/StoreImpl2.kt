/**
 * Copyright (c) 2025 lcaohoanq. All rights reserved.
 * This software is the confidential and proprietary information of lcaohoanq.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with lcaohoanq.
 */
package com.example.nocket.repositories

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StoreImpl2 @Inject constructor(@ApplicationContext context: Context) : Store {
    private val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    override fun getValue(key: String): String = sharedPreferences.getString(key, "") ?: ""

    override fun setValue(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }
}
