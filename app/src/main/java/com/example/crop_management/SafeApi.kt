package com.example.crop_management

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context


class SafeApi:Application(){
    companion object{
        @SuppressLint("stattic fieldLeak")
        private lateinit var context:Context
    }

    override fun onCreate() {
        super.onCreate()
        context=applicationContext
    }
}