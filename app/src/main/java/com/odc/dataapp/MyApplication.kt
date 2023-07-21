package com.odc.dataapp

import android.app.Application
import android.util.Log
import com.odc.dataapp.DB.MaBaseDeDonne

class MyApplication : Application() {

    val db by lazy {
        MaBaseDeDonne.getInstance(this)
    }
    override fun onCreate() {
        super.onCreate()
        Log.i("INIT", "DEMARRAGE APPLICATION")
    }
}