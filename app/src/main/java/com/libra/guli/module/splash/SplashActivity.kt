package com.libra.guli.module.splash

import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.view.View
import android.view.WindowManager
import com.libra.core.view.BaseActivity
import com.libra.guli.module.home.MainActivity
import com.libra.guli.R
import com.libra.guli.module.splash.viewmodel.SplashViewModel

class SplashActivity : BaseActivity<SplashViewModel>() {
    override fun getLayoutID(): Int = R.layout.activity_splash
    override fun initIntentData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val params = window.attributes
            params.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or params.flags
        }
    }

    override fun initViewModel() {
        viewModel?.imageBackground?.value = R.drawable.ic_splash
    }

    override fun initCustomView() {
        Handler().postDelayed({
            MainActivity.start(this)
            finish()
        }, 1000)
    }
}
