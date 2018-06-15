package com.libra.guli.module.calendar.model

/**
 * @author Libra
 * @since 2018/6/13
 */
class CalendarDate {

    var lunar = Lunar()//农历
    var solar = Solar()//公历
    private var isInThisMonth: Boolean = false //是否在当月
    private var isSelect: Boolean = false//是否被选中

    constructor(year: Int, month: Int, day: Int, isInThisMonth: Boolean, isSelect: Boolean, lunar: Lunar) {
        this.isInThisMonth = isInThisMonth
        this.isSelect = isSelect
        this.lunar = lunar
    }


    constructor(isInThisMonth: Boolean, isSelect: Boolean, solar: Solar, lunar: Lunar) {
        this.isInThisMonth = isInThisMonth
        this.isSelect = isSelect
        this.solar = solar
        this.lunar = lunar
    }

    fun isInThisMonth(): Boolean {
        return isInThisMonth
    }

    fun setIsInThisMonth(isInThisMonth: Boolean) {
        this.isInThisMonth = isInThisMonth
    }

    fun isSelect(): Boolean {
        return isSelect
    }

    fun setIsSelect(isSelect: Boolean) {
        this.isSelect = isSelect
    }

    fun setInThisMonth(inThisMonth: Boolean) {
        isInThisMonth = inThisMonth
    }

    fun setSelect(select: Boolean) {
        isSelect = select
    }
}
