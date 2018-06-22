package com.libra.guli.module.schedule

import android.app.Activity
import android.app.TimePickerDialog
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.libra.core.view.BaseActivity
import com.libra.guli.Constant
import com.libra.guli.R
import com.libra.guli.dao.model.Schedule
import com.libra.guli.module.calendar.utils.DateUtils
import com.libra.guli.module.schedule.viemodel.ScheduleViewModel
import com.libra.utils.startActivity
import com.libra.utils.toast
import java.text.SimpleDateFormat
import java.util.*

class ScheduleEditActivity : BaseActivity<ScheduleViewModel>() {
    companion object {
        fun start(activity: Activity, schedule: Schedule? = null) {
            activity.startActivity<ScheduleEditActivity>(Constant.IntentParam.Object to schedule)
        }
    }

    override fun getLayoutID() = R.layout.activity_schedule_edit

    override fun initViewModel() {
        val schedule = intent.getSerializableExtra(Constant.IntentParam.Object) as Schedule?
        if (schedule != null) {
            viewModel?.schedule = schedule
        }
        viewModel?.vibrate?.value = true
        if (viewModel?.schedule?.remind == null) {
            viewModel?.remind?.value = getString(R.string.not)
        } else {
            val remind = viewModel?.schedule?.remind ?: 0
            when {
                remind == 0 -> viewModel?.remind?.value = getString(R.string.not)
                remind >= 60 -> viewModel?.remind?.value = getString(R.string.remind_hour, remind / 60)
                remind >= 3600 -> viewModel?.remind?.value = getString(R.string.remind_day, remind / 3600)
                else -> viewModel?.remind?.value = getString(R.string.remind_minute, remind)
            }
        }
        viewModel?.schedule?.timeL = DateUtils.currentTimeMillis
        viewModel?.timer?.value = getString(R.string.remind_time, DateUtils.getHour(viewModel?.schedule?.timeL
                ?: 0), DateUtils.getMinute(viewModel?.schedule?.timeL ?: 0))
        viewModel?.timerClick = View.OnClickListener {
            val hour = DateUtils.hour
            val minute = DateUtils.minute
            TimePickerDialog(this,
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        viewModel?.timer?.value = getString(R.string.remind_time, hourOfDay, minute)
                        val dateFormatStr = DateUtils.getTime(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())) + getString(R.string.hour_minute, hourOfDay, minute) + ":00"
                        viewModel?.schedule?.timeL = DateUtils.date2TimeStamp(dateFormatStr, "yyyy-MM-dd HH:mm:ss")
                    }, hour, minute, true).show()
        }
        viewModel?.remindClick = View.OnClickListener { }
        viewModel?.ringClick = View.OnClickListener { }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(0, Menu.FIRST + 1, 0,
                getString(R.string.save))?.setShowAsActionFlags(
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
