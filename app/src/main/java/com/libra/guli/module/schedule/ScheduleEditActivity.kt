package com.libra.guli.module.schedule

import android.app.Activity
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.media.Ringtone
import android.media.RingtoneManager
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AlertDialogLayout
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.libra.core.view.BaseActivity
import com.libra.guli.Constant
import com.libra.guli.R
import com.libra.guli.dao.DaoManager
import com.libra.guli.dao.GLDatabase
import com.libra.guli.dao.model.Schedule
import com.libra.guli.module.calendar.utils.DateUtils
import com.libra.guli.module.schedule.viemodel.ScheduleViewModel
import com.libra.utils.hideSoftKeyboard
import com.libra.utils.startActivity
import com.libra.utils.toast
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
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
        viewModel?.content?.observe(this, android.arch.lifecycle.Observer { s -> viewModel?.schedule?.content = s })
        viewModel?.vibrate?.value = viewModel?.schedule?.vibrate == 1
        if (viewModel?.schedule?.remind == null) {
            viewModel?.remind?.value = getString(R.string.not)
        } else {
            val remind = viewModel?.schedule?.remind ?: 0
            when {
                remind == 0 -> viewModel?.remind?.value = getString(R.string.not)
                remind == 1 -> viewModel?.remind?.value = getString(R.string.remind_1)
                remind >= 60 -> viewModel?.remind?.value = getString(R.string.remind_hour, remind / 60)
                remind >= 1440 -> viewModel?.remind?.value = getString(R.string.remind_day, remind / 3600)
                else -> viewModel?.remind?.value = getString(R.string.remind_minute, remind)
            }
        }
        if (viewModel?.schedule?.timeL == 0L) {
            viewModel?.schedule?.timeL = DateUtils.currentTimeMillis
        }
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
        viewModel?.remindClick = View.OnClickListener {
            AlertDialog.Builder(this).setSingleChoiceItems(R.array.remind,
                    viewModel?.remindPosition ?: 0
            ) { dialog, which ->
                viewModel?.remindPosition = which
                viewModel?.remind?.value = resources.getStringArray(R.array.remind)[which]
                viewModel?.schedule?.remind = resources.getIntArray(R.array.remind_value)[which]
                dialog.dismiss()
            }.show()
        }
        viewModel?.ringClick = View.OnClickListener {
            if (viewModel?.ringList != null && viewModel?.ringList?.size!! > 0) {
                val array = arrayOfNulls<String>(viewModel?.ringList!!.size)
                var index = 0
                for (i in viewModel?.ringList!!.indices) {
                    val ring = viewModel?.ringList!![i]
                    array[i] = ring.getTitle(this)
                    if (array[i].equals(viewModel?.ringtone?.getTitle(this))) {
                        index = i
                    }
                }
                AlertDialog.Builder(this).setSingleChoiceItems(array,
                        index
                ) { dialog, which ->
                    viewModel?.ringtone = viewModel?.ringList!![which]
                    viewModel?.ring?.value = viewModel?.ringtone?.getTitle(this)
                    viewModel?.schedule?.ring = viewModel?.ring?.value
                    dialog.dismiss()
                }.show()
            }
        }
        viewModel?.ringList = getRingtoneList() as ArrayList<Ringtone>
        if (viewModel?.schedule?.ring == null) {
            viewModel?.ringtone = getDefaultRingtone()
            viewModel?.schedule?.ring = viewModel?.ringtone?.getTitle(this)
        } else {
            for (ring in viewModel?.ringList!!) {
                if (ring.getTitle(this) == viewModel?.schedule?.ring) {
                    viewModel?.ringtone = ring
                    break
                }
            }
        }
        viewModel?.ring?.value = viewModel?.ringtone?.getTitle(this)
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
                if (viewModel?.content?.value.isNullOrEmpty()) {
                    toast(getString(R.string.toast_input_content))
                } else {
                    DaoManager.getInstance(this).saveSchedule(viewModel?.schedule!!).subscribe({
                        currentFocus?.hideSoftKeyboard()
                        finish()
                    }, { e ->
                        toast(e?.message)
                    })
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        currentFocus?.hideSoftKeyboard()
        super.onBackPressed()
    }

    private fun getDefaultRingtone(): Ringtone {
        return RingtoneManager.getRingtone(this, RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_RINGTONE))
    }

    private fun getRingtoneList(): List<Ringtone> {
        val resArr = ArrayList<Ringtone>()
        val manager = RingtoneManager(this)
        val cursor = manager.cursor
        val count = cursor.count
        for (i in 0 until count) {
            resArr.add(manager.getRingtone(i))
        }
        return resArr

    }
}
