package com.libra.guli

import android.app.Application
import com.libra.core.AppContext

/**
 * @author Libra
 * @since 2018/6/13
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppContext.setApplication(this)
    }
}