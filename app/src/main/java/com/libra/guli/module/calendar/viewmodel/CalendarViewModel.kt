package com.libra.guli.module.calendar.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.libra.core.AppContext
import com.libra.core.viewmodel.BaseViewModel
import com.libra.guli.module.calendar.model.CalendarDate
import com.libra.guli.module.calendar.model.Lunar
import com.libra.guli.module.calendar.model.Solar
import com.libra.guli.module.calendar.utils.CalendarUtils
import com.libra.guli.module.calendar.utils.DateUtils
import com.libra.guli.module.calendar.utils.LunarSolarConverter
import java.text.ParseException


/**
 * @author Libra
 * @since 2018/6/15
 */
class CalendarViewModel : BaseViewModel {
    constructor() : super(AppContext.getApplication())

    constructor(application: Application) : super(application)

    var yearMonth = MutableLiveData<String>()
    var nextClick: View.OnClickListener? = null
    var preClick: View.OnClickListener? = null
    var menuClick: View.OnClickListener? = null


    //for calendarMonthFragment
    var itemLayoutId = MutableLiveData<Int>()
    var itemViewModelList = MutableLiveData<ArrayList<CalendarItemViewModel>>()

    fun getCalendarDate(year: Int, month: Int) {
        val mListDate = ArrayList<CalendarItemViewModel>()
        var list: List<CalendarUtils.CalendarSimpleDate>? = null
        try {
            list = CalendarUtils.getEverydayOfMonth(year, month)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        if (list != null) {
            for (i in 0 until list.size) {
                val solar = Solar()
                solar.solarYear = list[i].year
                solar.solarMonth = list[i].month
                solar.solarDay = list[i].day
                val lunar = LunarSolarConverter.SolarToLunar(solar)
                val item = CalendarItemViewModel()
                item.solarDay.value = solar.solarDay.toString()
                item.lunarDay.value = (solar.solar24Term
                        ?: solar.solarFestivalName) ?: Lunar.getChinaDayString(lunar.lunarDay)
                item.isToday.value = DateUtils.isToday(solar.solarYear, solar.solarMonth, solar.solarDay)
                item.isSelected.value = item.isToday.value
                mListDate.add(item)
            }
        }
        itemViewModelList.value = mListDate
    }
}