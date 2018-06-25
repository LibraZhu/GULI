package com.libra.guli.module.countdown

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.libra.core.view.BaseActivity
import com.libra.guli.Constant
import com.libra.guli.R
import com.libra.guli.dao.model.Schedule
import com.libra.guli.module.countdown.viewmodel.CountdownViewModel
import com.libra.guli.module.schedule.ScheduleEditActivity
import com.libra.utils.startActivity

class CountdownActivity : BaseActivity<CountdownViewModel>() {
    companion object {
        fun start(activity: Activity) {
            activity.startActivity<CountdownActivity>()
        }
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_countdown
    }

    override fun initViewModel() {
        viewModel?.itemLayoutId?.value = R.layout.item_coundown
        viewModel?.itemViewModelList?.value = ArrayList()
        viewModel?.getCountdownList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(0, Menu.FIRST + 1, 0,
                getString(R.string.add))?.setShowAsActionFlags(
                MenuItem.SHOW_AS_ACTION_IF_ROOM)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            Menu.FIRST + 1 -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
