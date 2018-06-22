package com.libra.guli.module.home

import android.app.Activity
import android.os.Build
import android.support.graphics.drawable.AnimationUtilsCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.widget.RelativeLayout
import android.widget.Toast
import com.libra.core.view.BaseActivity
import com.libra.core.viewmodel.BaseViewModel
import com.libra.guli.R
import com.libra.guli.module.calendar.CalendarFragment
import com.libra.guli.module.home.viewmodel.MainItemViewModel
import com.libra.guli.module.home.viewmodel.MainViewModel
import com.libra.guli.module.schedule.ScheduleEditActivity
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

        visibleCalendar(false)
        viewModel?.arrowClick = View.OnClickListener {
            visibleCalendar(viewModel?.calendarVisible?.value?.not() ?: false)
        }
    }

    override fun initToolBar() {
        super.initToolBar()
        showBackButton(false)
        toolbarTitle?.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, R.drawable.ic_home_logo), null, null, null)
    }

    override fun initCustomView() {
        (recyclerView.layoutManager as LinearLayoutManager).orientation = LinearLayoutManager.HORIZONTAL
        supportFragmentManager.beginTransaction().replace(R.id.container, CalendarFragment.newInstance(), "calendar").commit()
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
            Menu.FIRST + 1 -> ScheduleEditActivity.start(this)
            Menu.FIRST + 2 -> toast("countdown")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (viewModel?.calendarVisible?.value == true) {
            visibleCalendar(false)

        } else {
            super.onBackPressed()
        }
    }

    var x1 = 0f
    var x2 = 0f
    var y1 = 0f
    var y2 = 0f
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.x
            y1 = event.y
        }
        if (event?.action == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.x
            y2 = event.y
            if (y1 > resources.displayMetrics.heightPixels * 2 / 3) {
                if (y1 - y2 > 50) {
                    visibleCalendar(true)
                } else if (y2 - y1 > 50) {
                    visibleCalendar(true)
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun visibleCalendar(visible: Boolean) {
        viewModel?.calendarVisible?.value = visible
        if (viewModel?.calendarVisible?.value == true) {
            val params = image_arrow.layoutParams as RelativeLayout.LayoutParams
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                params.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
            } else {
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0)
            }
            params.addRule(RelativeLayout.ABOVE, R.id.container)
            image_arrow.layoutParams = params
        } else {
            val params = image_arrow.layoutParams as RelativeLayout.LayoutParams
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                params.removeRule(RelativeLayout.ABOVE)
            } else {
                params.addRule(RelativeLayout.ABOVE, 0)
            }
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
            image_arrow.layoutParams = params
        }
    }
}
