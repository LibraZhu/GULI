package com.libra.guli.module.home.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.view.View
import android.widget.AdapterView
import com.libra.core.AppContext
import com.libra.core.viewmodel.BaseViewModel
import com.libra.guli.App
import com.libra.guli.R
import com.libra.guli.dao.DaoManager
import com.libra.guli.dao.model.Countdown
import com.libra.guli.dao.model.Schedule
import com.libra.guli.module.calendar.model.Lunar
import com.libra.guli.module.calendar.model.Solar
import com.libra.guli.module.calendar.utils.CalendarUtils
import com.libra.guli.module.calendar.utils.DateUtils
import com.libra.guli.module.calendar.utils.LunarSolarConverter
import com.libra.guli.module.schedule.ScheduleEditActivity
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.ParseException

/**
 * @author Libra
 * @since 2018/6/13
 */
class MainViewModel : BaseViewModel {
    constructor() : super(AppContext.getApplication())

    constructor(application: Application) : super(application)

    var cd1: Countdown? = null
    var cd2: Countdown? = null
    var cd3: Countdown? = null
    var countdown1 = MutableLiveData<String>()
    var countdown2 = MutableLiveData<String>()
    var countdown3 = MutableLiveData<String>()
    var imageBackground = MutableLiveData<Int>()

    var itemLayoutId = MutableLiveData<Int>()
    var itemViewModelList = MutableLiveData<ArrayList<BaseViewModel>>()
    var itemClick: ((schedule: Schedule) -> Unit)? = null

    var calendarVisible = MutableLiveData<Boolean>()
    var arrowClick: View.OnClickListener? = null


    fun getScheduleList() {
        DaoManager.getInstance(getApplication()).getScheduleList().subscribe { list ->
            val itemList = ArrayList<BaseViewModel>()
            if (list != null) {
                for (schedule in list) {
                    val item = MainItemViewModel()
                    item.schedule = schedule
                    item.itemClick = View.OnClickListener { itemClick?.invoke(schedule) }
                    item.content.value = DateUtils.timeStamp2Date(schedule.timeL, "HH:mm").replace(":", "") + " ·" + schedule.content
                    itemList.add(item)
                    itemViewModelList.value = itemList
                }
            }
        }
    }

    fun getShowCountdown() {
        DaoManager.getInstance(getApplication()).getCountdownList().flatMap { t ->
            if (t.isEmpty()) {
                val countdownList = java.util.ArrayList<Countdown>()
                for (m in 1 until 13) {
                    var list: List<CalendarUtils.CalendarSimpleDate>? = null
                    try {
                        list = CalendarUtils.getEverydayOfMonthSimple(DateUtils.year, m)
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }

                    if (list != null) {
                        for (i in 0 until list!!.size) {
                            val solar = Solar()
                            solar.solarYear = list!![i].year
                            solar.solarMonth = list!![i].month
                            solar.solarDay = list!![i].day
                            val lunar = LunarSolarConverter.SolarToLunar(solar)
                            if (lunar.isLFestival) {
                                val countdown = Countdown()
                                countdown.content = lunar.lunarFestivalName
                                countdown.des = getApplication<App>().getString(R.string.lunar, DateUtils.getConvertMonth(lunar.lunarMonth), Lunar.getChinaDayString(lunar.lunarDay))
                                countdown.timeL = DateUtils.date2TimeStamp(getApplication<App>().getString(R.string.ymdhms, solar.solarYear, solar.solarMonth, solar.solarDay), "yyyy-MM-dd HH:mm:ss")
                                countdown.type = Countdown.TYPE_FESTIVAL
                                countdownList.add(countdown)
                            } else if (!solar.solar24Term.isNullOrEmpty()) {
                                val countdown = Countdown()
                                countdown.content = solar.solar24Term
                                countdown.des = getApplication<App>().getString(R.string.solar_term, DateUtils.getConvertMonth(lunar.lunarMonth), Lunar.getChinaDayString(lunar.lunarDay))
                                countdown.timeL = DateUtils.date2TimeStamp(getApplication<App>().getString(R.string.ymdhms, solar.solarYear, solar.solarMonth, solar.solarDay), "yyyy-MM-dd HH:mm:ss")
                                countdown.type = Countdown.TYPE_TERM
                                countdownList.add(countdown)
                            } else if (solar.isSFestival) {
                                val countdown = Countdown()
                                countdown.content = solar.solarFestivalName
                                countdown.des = getApplication<App>().getString(R.string.solar, DateUtils.getConvertMonth(solar.solarMonth), Lunar.getDayString(solar.solarDay))
                                countdown.timeL = DateUtils.date2TimeStamp(getApplication<App>().getString(R.string.ymdhms, solar.solarYear, solar.solarMonth, solar.solarDay), "yyyy-MM-dd HH:mm:ss")
                                countdown.type = Countdown.TYPE_FESTIVAL
                                countdownList.add(countdown)
                            }
                        }
                    }
                }
                DaoManager.getInstance(getApplication()).saveCountdownListSimple(countdownList)
            }
            return@flatMap DaoManager.getInstance(getApplication()).getShowCountdownList().map { list ->
                val listNew = ArrayList<Countdown>()
                if (list.isEmpty()) {
                    val recentList = DaoManager.getInstance(getApplication()).getRecentCountdownList()
                    if (recentList.isNotEmpty()) {
                        listNew.add(recentList[0])
                    }
                } else {
                    listNew.addAll(list)
                }
                return@map listNew
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe { list ->
            if (list.size > 0) {
                cd1 = list[0]
                val day = (((cd1?.timeL ?: 0) - System.currentTimeMillis()) / 1000 / 60 / 60 / 24)
                countdown3.value = cd1?.content + getApplication<App>().getString(R.string.countdown) + String.format("%02d", day + 1) + getApplication<App>().getString(R.string.day)
            }
            if (list.size > 1) {
                cd2 = list[1]
                val day = (((cd2?.timeL ?: 0) - System.currentTimeMillis()) / 1000 / 60 / 60 / 24)
                countdown3.value = cd2?.content + getApplication<App>().getString(R.string.countdown) + String.format("%02d", day + 1) + getApplication<App>().getString(R.string.day)
            }
            if (list.size > 2) {
                cd3 = list[2]
                val day = (((cd3?.timeL ?: 0) - System.currentTimeMillis()) / 1000 / 60 / 60 / 24)
                countdown3.value = cd3?.content + getApplication<App>().getString(R.string.countdown) + String.format("%02d", day + 1) + getApplication<App>().getString(R.string.day)
            }
        }
    }
}