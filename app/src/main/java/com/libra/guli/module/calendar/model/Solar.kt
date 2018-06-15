package com.libra.guli.module.calendar.model

/**
 * @author Libra
 * @since 2018/6/13
 */
class Solar {
    var solarDay: Int = 0
    var solarMonth: Int = 0
    var solarYear: Int = 0
    var isSFestival: Boolean = false
    var solarFestivalName: String? = null//公历节日
    var solar24Term: String? = null//24节气

    override fun toString(): String {
        return ("Solar [solarDay=" + solarDay + ", solarMonth=" + solarMonth
                + ", solarYear=" + solarYear + ", isSFestival=" + isSFestival
                + ", solarFestivalName=" + solarFestivalName + ", solar24Term="
                + solar24Term + "]")
    }

}
