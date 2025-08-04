package com.example.mvvm.repositories

interface MainLog {
   fun d(tag: String, msg: String)
   fun i(tag: String, msg: String)
   fun e(tag: String, msg: String)
}