package com.libra.guli.module.calendar


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.libra.core.view.BaseFragment

import com.libra.guli.R
import com.libra.guli.module.calendar.viewmodel.CalendarViewModel

class CalendarMonthFragment : BaseFragment<CalendarViewModel>() {
    override fun getLayoutID(): Int {
        return R.layout.fragment_calendar_month
    }
}
