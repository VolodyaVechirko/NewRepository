package com.vvechirko.newrepository

import android.app.Application
import com.vvechirko.newrepository.data.AppDatabase

class App : Application() {

    companion object {
        private lateinit var instance: App

        fun get() = instance

        val db: AppDatabase by lazy {
            AppDatabase.create(get().applicationContext)
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}