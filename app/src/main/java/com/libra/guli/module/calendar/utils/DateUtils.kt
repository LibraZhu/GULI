package com.libra.guli.module.calendar.utils


import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

/**
 * Created by joybar on 2/16/16.
 */
object DateUtils {

    internal var cal = Calendar.getInstance()

    //获取年份
    val year: Int
        get() = cal.get(Calendar.YEAR)

    //获取月份
    val month: Int
        get() = cal.get(Calendar.MONTH) + 1

    //获取日
    val day: Int
        get() = cal.get(Calendar.DATE)

    //  return  cal.get(Calendar.HOUR);//获取小时,12小时制
    // 24小时制
    val hour: Int
        get() = cal.get(Calendar.HOUR_OF_DAY)

    //获取分
    val minute: Int
        get() = cal.get(Calendar.MINUTE)

    //获取秒
    val second: Int
        get() = cal.get(Calendar.SECOND)

    val week: String
        get() {

            val weeks = arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
            val cal = Calendar.getInstance()
            cal.time = Date()
            var week_index = cal.get(Calendar.DAY_OF_WEEK) - 1
            if (week_index < 0) {
                week_index = 0
            }
            return weeks[week_index]
        }

    fun getHour(date: Long): Int {
        val cal = Calendar.getInstance()
        cal.timeInMillis = date
        return cal.get(Calendar.HOUR_OF_DAY)
    }

    fun getMinute(date: Long): Int {
        val cal = Calendar.getInstance()
        cal.timeInMillis = date
        return cal.get(Calendar.MINUTE)
    }

    /**
     * 获取当前时间戳
     *
     * @return
     */
    // return time / 1000;
    val currentTimeMillis: Long
        get() = System.currentTimeMillis()


    fun getWeek(date: Long): String {

        val weeks = arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
        val cal = Calendar.getInstance()
        cal.timeInMillis = date
        var week_index = cal.get(Calendar.DAY_OF_WEEK) - 1
        if (week_index < 0) {
            week_index = 0
        }
        return weeks[week_index]
    }


    /**
     * 实现给定某日期，判断是星期几
     *
     * @param date 必须yyyy-MM-dd
     * @return
     */
    fun getWeekday(date: String): String {
        val sd = SimpleDateFormat("yyyy-MM-dd")
        val sdw = SimpleDateFormat("E")
        var d: Date? = null
        try {
            d = sd.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return sdw.format(d)
    }

    /**
     * @param sdf
     * @return
     */
    fun getTime(sdf: SimpleDateFormat): String {
        //"yyyy-MM-dd HH:mm:ss aa",最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制
        return sdf.format(Date())
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date_str   字符串日期 “2016-02-26 12:00:00”
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    fun date2TimeStamp(date_str: String, format: String): Long {
        try {
            val sdf = SimpleDateFormat(format)
            //  return sdf.parse(date_str).getTime() / 1000;
            return sdf.parse(date_str).time
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return 0
    }

    /**
     * 时间戳字符串转换成日期格式
     *
     * @param seconds 精确到毫秒
     * @param format  如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    fun timeStamp2Date(seconds: Long, format: String?): String {
        var format = format

        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss"
        }

        val sdf = SimpleDateFormat(format)
        // return sdf.format(new Date(Long.valueOf(seconds+"000")));
        return sdf.format(Date(seconds))
    }

    /**
     * @param dateStr 2008-08-08 12:10:12
     * @param format  yyyy-MM-dd HH:mm:ss
     * @return
     */
    fun getFormatDate(dateStr: String, format: String): String {

        val sdf = SimpleDateFormat(format)
        try {
            return sdf.format(sdf.parse(dateStr))
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return ""
    }

    fun isToday(year: Int, month: Int, day: Int): Boolean {
        return year == DateUtils.year && month == DateUtils.month && day == DateUtils.day
    }

    /**
     * 判断是否为今天或者今天以后
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    fun isAfterToday(year: Int, month: Int, day: Int): Boolean {

        return if (year > DateUtils.year) {
            true
        } else if (year == DateUtils.year) {

            if (month > DateUtils.month) {
                true
            } else if (month == DateUtils.month) {
                if (day >= DateUtils.day) {
                    true
                } else {
                    false
                }

            } else {
                false
            }

        } else {
            false
        }


    }

    /**
     * 判断是否为当月或者当月以后
     *
     * @param year
     * @param month
     * @return
     */
    fun isAfterThisMonth(year: Int, month: Int): Boolean {

        return if (year > DateUtils.year) {
            true
        } else if (year == DateUtils.year) {

            if (month >= DateUtils.month) {
                true
            } else {
                false
            }

        } else {
            false
        }


    }

    fun getConvertMonth(numberStr: String): String {

        val months = arrayOf("一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月")

        try {
            val month = Integer.valueOf(numberStr)
            return months[month - 1]
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }
}
