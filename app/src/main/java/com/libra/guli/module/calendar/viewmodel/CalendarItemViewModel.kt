package com.libra.guli.module.calendar.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableBoolean
import android.view.View
import com.libra.core.viewmodel.BaseViewModel

/**
 * @author Libra
 * @since 2018/6/15
 */
class CalendarItemViewModel : BaseViewModel() {
    var solarDay = MutableLiveData<String>()
    var lunarDay = MutableLiveData<String>()
    var hasSchedule = MutableLiveData<Boolean>()
    var isToday = MutableLiveData<Boolean>()
    var isGray = MutableLiveData<Boolean>()
    var isSelected = MutableLiveData<Boolean>()
    var itemClick: View.OnClickListener? = null
}