package com.filelucker.englishdatetobangladate

import java.util.*


class Bangla {
    private var ENGLISH_TO_BANGLA_DATE: Map<String, Date<BanglaMonth>>? = null
    private var BANGLA_TO_ENGLISH_DATE: Map<String, Date<EnglishMonth>>? = null
    private var ENGLISH_MONTH: Array<EnglishMonth>

    //Load static Map and Array for faster conversion
    init {
        ENGLISH_TO_BANGLA_DATE = HashMap()
        BANGLA_TO_ENGLISH_DATE = HashMap()
        ENGLISH_MONTH = EnglishMonth.values()
        val start: Calendar = GregorianCalendar.getInstance()
        start.set(Calendar.HOUR_OF_DAY, 0)
        start.set(Calendar.MINUTE, 0)
        start.set(Calendar.SECOND, 0)
        start.set(Calendar.MILLISECOND, 0)
        start.set(2014, 3, 14)
        val end: Calendar = GregorianCalendar.getInstance()
        end.set(Calendar.HOUR_OF_DAY, 0)
        end.set(Calendar.MINUTE, 0)
        end.set(Calendar.SECOND, 0)
        end.set(Calendar.MILLISECOND, 0)
        end.set(2015, 3, 14)
        val interatorBanglaMonth: Iterator<BanglaMonth> = EnumSet.allOf(
            BanglaMonth::class.java
        ).iterator()
        var currentMonthName: BanglaMonth = interatorBanglaMonth.next()
        var currentDay = 1

        //Start from April 14 of non-leap year because that 1st day of Bengali year
        while (start.before(end)) {
            (ENGLISH_TO_BANGLA_DATE as HashMap<String, Date<BanglaMonth>>).put(
                start[GregorianCalendar.MONTH].toString() + "/" + start[GregorianCalendar.DATE],
                Date<BanglaMonth>().day(currentDay).month(currentMonthName)
            )
            currentDay++
            /* The first five months of the year from Bôishakh to Bhadrô will
			 * consist of 31 days each */
            /* The first five months of the year from Bôishakh to Bhadrô will
			 * consist of 31 days each */if (currentMonthName.ordinal < 5 && currentDay > 31) {
                currentDay = 1
                currentMonthName = interatorBanglaMonth.next()
            } else if (currentMonthName.ordinal > 4 && currentDay > 30 && interatorBanglaMonth.hasNext()) {
                currentDay = 1
                currentMonthName = interatorBanglaMonth.next()
            }
            start.add(GregorianCalendar.DATE, 1)
        }

        //Now populate banglaToEnglishDate map based on englishToBanglaDate map for better performance
        val englishToBanglaDateEntrySet: Set<Map.Entry<String, Date<BanglaMonth>>> =
            ENGLISH_TO_BANGLA_DATE!!.entries
        val englishToBanglaDateIterator: Iterator<Map.Entry<String, Date<BanglaMonth>>> =
            englishToBanglaDateEntrySet.iterator()
        while (englishToBanglaDateIterator.hasNext()) {
            val (key, value) = englishToBanglaDateIterator.next()
            (BANGLA_TO_ENGLISH_DATE as HashMap<String, Date<EnglishMonth>>).put(
                getEnglishToBanglaMapKey(
                    value
                ),
                getEnglishToBanglaMapValue(key)
            )
        }

        // Add leap year manually for Bangla
        (ENGLISH_TO_BANGLA_DATE as HashMap<String, Date<BanglaMonth>>).put(
            "1/29",
            Date<BanglaMonth>().day(17).month(BanglaMonth.FALGUN)
        )
        (BANGLA_TO_ENGLISH_DATE as HashMap<String, Date<EnglishMonth>>).put(
            "10/31",
            Date<EnglishMonth>().day(15).month(EnglishMonth.MARCH)
        )
    }

    private fun getEnglishToBanglaMapKey(banglaDate: Date<BanglaMonth>): String {
        return banglaDate.getMonth()!!.ordinal.toString()+"/"+banglaDate.getDay();
    }

    private fun getEnglishToBanglaMapValue(banglaToEnglishKey: String): Date<EnglishMonth> {
        val date = banglaToEnglishKey.split("/").toTypedArray()
        return Date<EnglishMonth>().month(
            ENGLISH_MONTH[date[0].toInt()]
        ).day(date[1].toInt())

    }

    /**
     * This method takes English date as parameter returns Bengali date
     * @param year
     * @param month
     * @param day
     * @return Date<BanglaMonth>
    </BanglaMonth> */
    fun getBanglaDate(year: Int, month: Int, day: Int): Date<BanglaMonth> {
        //As month in Map key is 0 based (0-11)
        var month = month
        month--
        val banglaDay: Int
        val banglaYear: Int
        val banglaMonth: BanglaMonth
        // Bangla calendar start from April 14, 593.
        var banglaYearStarted = 593

        // Create key based of the provided date
        val key = "$month/$day"

        /* Get Day from Map. If the date is between March 1 and March 14
		 * then add 1 day to adjust with leap year*/banglaDay = if (isLeapYear(year)
            && month == 2 && day < 15
        ) {
            ENGLISH_TO_BANGLA_DATE!![key]!!.getDay() + 1
        } else {
            ENGLISH_TO_BANGLA_DATE!![key]!!.getDay()
        }

        // Get Month Name from Map
        banglaMonth = ENGLISH_TO_BANGLA_DATE!![key]!!.getMonth()!!

        // Calculate Bangla Year from the English year.
        if (month < 3
            || month == 3 && day < 14
        ) {
            banglaYearStarted++
        }
        banglaYear = year - banglaYearStarted
        return Date<BanglaMonth>().year(banglaYear).month(banglaMonth).day(banglaDay)
    }

    /**
     * This method takes Bengali date as parameter returns English date
     * @param banglaYear
     * @param banglaMonth
     * @param banglaDay
     * @return Date<EnglishMonth>
    </EnglishMonth> */
    fun getEnglishDate(banglaYear: Int, banglaMonth: Int, banglaDay: Int): Date<EnglishMonth> {
        var banglaMonth = banglaMonth
        banglaMonth--
        var englishDay: Int
        val englishYear: Int
        var englishMonth: EnglishMonth
        // Bangla calendar start from April 14, 593.
        var banglaYearStarted = 593

        // Calculate English Year from the Bangla year.
        if (banglaMonth > 8
            || banglaMonth == 8 && banglaDay > 17
        ) {
            banglaYearStarted++
        }
        englishYear = banglaYear + banglaYearStarted

        // Create key based of the provided Bengali date
        val key = "$banglaMonth/$banglaDay"
        englishDay = BANGLA_TO_ENGLISH_DATE!![key]!!.getDay()
        englishMonth = BANGLA_TO_ENGLISH_DATE!![key]!!.getMonth()!!

        /* Get Day from Map. If the date is between March 1 and March 14 then
		 * subtract 1 day to adjust with leap year*/if (isLeapYear(englishYear)
            && banglaMonth == 10 && banglaDay > 16
        ) {
            englishDay--
            if (englishDay == 0) {
                englishDay = 29
                englishMonth = EnglishMonth.FEBRUARY
            }
        }

        return Date<EnglishMonth>().year(englishYear).month(englishMonth).day(englishDay)

    }

    /**
     * Returns true only if the provided year is leap year
     * @param year
     * @return
     */
    fun isLeapYear(year: Int): Boolean {
        return year % 400 == 0 || year % 4 == 0 && year % 100 != 0
    }
}