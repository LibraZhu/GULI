package com.libra.guli.module.home

import android.app.Activity
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.libra.core.view.BaseActivity
import com.libra.core.viewmodel.BaseViewModel
import com.libra.guli.R
import com.libra.guli.module.home.viewmodel.MainItemViewModel
import com.libra.guli.module.home.viewmodel.MainViewModel
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
        viewModel?.imageBackground?.value = R.drawable.ic_bg_main
        viewModel?.countdown3?.value = "七夕倒计时08天"
        viewModel?.itemLayoutId?.value = R.layout.item_main
        val itemList = ArrayList<BaseViewModel>()
        var item = MainItemViewModel()
        item.ocntent.value = "0945 ·打扫，院子"
        itemList.add(item)
        item = MainItemViewModel()
        item.ocntent.value = "0945 ·打扫,院子111"
        itemList.add(item)
        item = MainItemViewModel()
        item.ocntent.value = "0945 ·打扫。院子222、子222子222子222子222"
        itemList.add(item)
        viewModel?.itemViewModelList?.value = itemList
    }

    override fun initToolBar() {
        super.initToolBar()
        showBackButton(false)
        toolbarTitle?.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, R.drawable.ic_home_logo), null, null, null)
    }

    override fun initCustomView() {
        (recyclerView.layoutManager as LinearLayoutManager).orientation = LinearLayoutManager.HORIZONTAL
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
