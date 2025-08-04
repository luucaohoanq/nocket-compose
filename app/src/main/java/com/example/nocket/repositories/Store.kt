package com.example.nocket.repositories

interface Store {
    fun getValue(key: String): String
    fun setValue(key: String, value: String)
}