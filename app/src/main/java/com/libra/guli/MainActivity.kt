package com.libra.guli

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.libra.core.view.BaseActivity
import com.libra.guli.viewmodel.MainViewModel
import com.libra.utils.startActivity
import com.libra.utils.toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel>() {

    companion object {
        fun start(activity: Activity) {
            activity.startActivity<MainActivity>()
        }
    }

    override fun getLayoutID(): Int = R.layout.activity_main

    override fun initViewModel() {
    }

    override fun initToolBar() {
        super.initToolBar()
        showBackButton(false)
        toolbarTitle?.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, R.drawable.ic_home_logo), null, null, null)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(0, Menu.FIRST + 1, 0,
                getString(R.string.add))?.setIcon(R.drawable.ic_menu_add)?.setShowAsActionFlags(
                MenuItem.SHOW_AS_ACTION_IF_ROOM)
        menu?.add(0, Menu.FIRST + 2, 0,
                getString(R.string.countdown))?.setIcon(R.drawable.ic_menu_countdown)?.setShowAsActionFlags(
                MenuItem.SHOW_AS_ACTION_IF_ROOM)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            Menu.FIRST + 1 -> toast("add")
            Menu.FIRST + 2 -> toast("countdown")
        }
        return super.onOptionsItemSelected(item)
    }

}
