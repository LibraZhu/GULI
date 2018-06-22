package com.libra.guli.module.schedule.viemodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.libra.core.AppContext
import com.libra.core.viewmodel.BaseViewModel
import com.libra.guli.dao.model.Schedule
import com.libra.guli.module.calendar.utils.DateUtils

/**
 * @author Libra
 * @since 2018/6/21
 */
class ScheduleViewModel : BaseViewModel {
    constructor() : super(AppContext.getApplication())

    constructor(application: Application) : super(application)

    var schedule: Schedule = Schedule()
        set(value) {
            field = value
            timerL.value = value.timeL
            timer.value = value.time
            content.value = value.content
            timerL.value = value.timeL
            if (value.timeL == 0L) {
                field.timeL = DateUtils.currentTimeMillis
                timerL.value = field.timeL
            }
        }
    var timerL = MutableLiveData<Long>()
    var timer = MutableLiveData<String>()
    var content = MutableLiveData<String>()
    var timerClick: View.OnClickListener? = null
    var remind = MutableLiveData<String>()
    var ring = MutableLiveData<String>()
    var vibrate = MutableLiveData<Boolean>()
    var ringClick: View.OnClickListener? = null
    var remindClick: View.OnClickListener? = null
}