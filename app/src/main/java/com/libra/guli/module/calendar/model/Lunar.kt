package com.libra.guli.module.calendar.model

/**
 * @author Libra
 * @since 2018/6/13
 */
class Lunar {
    var isleap: Boolean = false
    var lunarDay: Int = 0
    var lunarMonth: Int = 0
    var lunarYear: Int = 0
    var isLFestival: Boolean = false
    var lunarFestivalName: String? = null//农历节日

    override fun toString(): String {
        return ("Lunar [isleap=" + isleap + ", lunarDay=" + lunarDay
                + ", lunarMonth=" + lunarMonth + ", lunarYear=" + lunarYear
                + ", isLFestival=" + isLFestival + ", lunarFestivalName="
                + lunarFestivalName + "]")
    }

    companion object {


        val chineseNumber = arrayOf("一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二")

        fun getChinaDayString(day: Int): String {
            val chineseTen = arrayOf("初", "十", "廿", "卅")
            val n = if (day % 10 == 0) 9 else day % 10 - 1
            if (day > 30) {
                return ""
            }
            return if (day == 10) {
                "初十"
            } else {
                chineseTen[day / 10] + chineseNumber[n]
            }
        }
    }
}
