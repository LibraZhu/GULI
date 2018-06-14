package com.libra.guli

import android.os.Handler
import com.libra.core.view.BaseActivity
import com.libra.guli.viewmodel.SplashViewModel

class SplashActivity : BaseActivity<SplashViewModel>() {
    override fun getLayoutID(): Int = R.layout.activity_splash

    override fun initViewModel() {
    }

    override fun initCustomView() {
        Handler().postDelayed({ MainActivity.start(this) }, 1000)
    }
}
