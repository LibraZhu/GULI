package com.libra.guli.module.calendar.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.text.TextUtils
import android.view.View
import com.libra.core.AppContext
import com.libra.core.viewmodel.BaseViewModel
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
    var mYear: Int = 0
    var mMonth: Int = 0
    var itemLayoutId = MutableLiveData<Int>()
    var itemViewModelList = MutableLiveData<ArrayList<BaseViewModel>>()

    fun getCalendarDate() {
        val mListDate = ArrayList<BaseViewModel>()
        var list: List<CalendarUtils.CalendarSimpleDate>? = null
        try {
            list = CalendarUtils.getEverydayOfMonth(mYear, mMonth)
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
                item.lunarDay.value = solar.solar24Term
                if (TextUtils.isEmpty(solar.solar24Term)) {
                    item.lunarDay.value = solar.solarFestivalName
                }
                if (TextUtils.isEmpty(solar.solarFestivalName)) {
                    item.lunarDay.value = Lunar.getChinaDayString(lunar.lunarDay)
                }
                item.isToday.value = DateUtils.isToday(solar.solarYear, solar.solarMonth, solar.solarDay)
                item.isSelected.value = item.isToday.value
                item.isGray.value = solar.solarMonth != mMonth
                mListDate.add(item)
            }
        }
        itemViewModelList.value = mListDate
    }
}