package com.example.sepiapets.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.sepiapets.model.WorkHour
import com.example.sepiapets.ui.activities.time
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Utility {
    companion object {
        // Returns true/false for checking working hour from config json
        @SuppressLint("SimpleDateFormat")
        fun checkForWorkingHour(workHour:WorkHour):Boolean{

            try{
                val splited: List<String> = workHour.settings.workHours.split(" ")
                val days = splited[0]

                if (!days.startsWith("S")){

                    val calendar: Calendar = Calendar.getInstance()
                    val currentTime = calendar.get(Calendar.HOUR_OF_DAY).toString()+":"+calendar.get(Calendar.MINUTE).toString()
                    val hourOfDay = parseDate(currentTime)
                    val startTime = parseDate(splited[1])
                    val endTime = parseDate(splited[3])
                    if (startTime!!.before( hourOfDay ) && endTime!!.after(hourOfDay)) {
                        return true
                    }
                    val daysList = listOf<String>("Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday")
                    val daysSplited: List<String> = days.split("-")
                    val startDay = daysSplited[0]
                    val endDay = daysSplited[1]
                    val startDayIndex = daysList.indexOfFirst { it.startsWith(startDay) }
                    val endDayIndex = daysList.indexOfFirst { it.startsWith(endDay) }

                    time = ""+ daysList[startDayIndex]+" - "+ daysList[endDayIndex]+" ,"+splited[1] + " to " + ""+splited[3]
                }
            }catch (e:Exception) {
                e.printStackTrace()
            }
            return false
        }

        //Returns formatted Time
        private fun parseDate(date: String): Date? {
            val inputFormat = "HH:mm"
            val inputParser = SimpleDateFormat(inputFormat, Locale.US)
            return try {
                inputParser.parse(date)
            } catch (e: ParseException) {
                Date(0)
            }
        }
    }

}