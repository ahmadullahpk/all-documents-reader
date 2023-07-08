package com.ahmadullahpk.alldocumentreader

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication


class Application : MultiDexApplication() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()


    }
}