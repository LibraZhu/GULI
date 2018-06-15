package com.libra.guli.module.calendar


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.libra.core.view.BaseFragment

import com.libra.guli.R
import com.libra.guli.module.calendar.viewmodel.CalendarViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class CalendarFragment : BaseFragment<CalendarViewModel>() {
    override fun getLayoutID(): Int {
        return R.layout.fragment_calendar
    }

    override fun initViewModel() {
        viewModel?.preClick = View.OnClickListener { }
        viewModel?.nextClick = View.OnClickListener { }
        viewModel?.menuClick = View.OnClickListener { }
    }


}
