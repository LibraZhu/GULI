package com.libra.guli.module.calendar


import android.arch.lifecycle.Observer
import android.content.Context
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.libra.core.view.BaseFragment

import com.libra.guli.R
import com.libra.guli.module.calendar.viewmodel.CalendarViewModel
import com.libra.utils.dp2px
import kotlinx.android.synthetic.main.fragment_calendar_month.*
import android.view.View.MeasureSpec
import android.util.TypedValue
import android.databinding.adapters.TextViewBindingAdapter.setTextSize
import android.databinding.adapters.TextViewBindingAdapter.setText
import android.widget.TextView


class CalendarMonthFragment : BaseFragment<CalendarViewModel>() {

    companion object {
        private val YEAR = "year"
        private val MONTH = "month"
        fun newInstance(year: Int, month: Int): CalendarMonthFragment {
            val fragment = CalendarMonthFragment()
            val args = Bundle()
            args.putInt(YEAR, year)
            args.putInt(MONTH, month)
            fragment.arguments = args
            return fragment
        }
    }

    var viewPagerHeight = 0
    override fun getLayoutID(): Int {
        return R.layout.fragment_calendar_month
    }

    override fun initViewModel() {
        super.initViewModel()
        if (arguments != null) {
            viewModel?.mYear = arguments?.getInt(YEAR) ?: 0
            viewModel?.mMonth = arguments?.getInt(MONTH) ?: 0
        }
        viewModel?.itemLayoutId?.value = R.layout.item_calendar
        viewModel?.getCalendarDate()
        viewModel?.itemViewModelList?.observe(this, Observer { list ->
            if (list != null) {
                viewPagerHeight = if (list.size > 35) {
                    ((context?.dp2px((30 + 6 * (3 + 30 + 3 + 1)).toFloat())
                            ?: 0) + 6 * getHeight("初", 10))
                } else {
                    ((context?.dp2px((30 + 5 * (3 + 30 + 3 + 1)).toFloat())
                            ?: 0) + 5 * getHeight("初", 10))
                }
            }
        })
    }

    private fun getHeight(text: String, textSize: Int): Int {
        val textView = TextView(context)
        textView.text = text
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize.toFloat())
        val widthMeasureSpec = MeasureSpec.makeMeasureSpec(resources.displayMetrics.widthPixels, MeasureSpec.AT_MOST)
        val heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        textView.measure(widthMeasureSpec, heightMeasureSpec)
        return textView.measuredHeight + 5
    }

    override fun initCustomView() {
        (recyclerView.layoutManager as GridLayoutManager).spanCount = 7
    }
}
