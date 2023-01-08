package com.filelucker.englishdatetobangladate

import android.annotation.SuppressLint
import java.time.DayOfWeek

class Date<T> {
    private var day = 0
    private var month: T? = null
    private var year = 0
    private var dayOfWeek: DayOfWeek? = null

    fun <T> newInstance(): Date<T> {
        return Date()
    }

    fun year(year: Int): Date<T> {
        this.year = year
        return this
    }

    fun month(month: T?): Date<T> {
        this.month = month
        return this
    }

    fun day(day: Int): Date<T> {
        this.day = day
        return this
    }

    fun dayOfWeek(dayOfWeek: DayOfWeek?): DayOfWeek? {
        this.dayOfWeek = dayOfWeek
        return dayOfWeek
    }

    fun getDay(): Int {
        return day
    }

    fun getMonth(): T? {
        return month
    }

    fun getYear(): Int {
        return year
    }

    fun getDayOfWeek(): DayOfWeek? {
        return dayOfWeek
    }

    @SuppressLint("DefaultLocale")
    override fun toString(): String {
        return java.lang.String.format("%s %d, %d", month, day, year)
    }
}