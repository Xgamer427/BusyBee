package com.example.myapplication.data

import android.annotation.SuppressLint

class JsonToSaveForPersistance(var listOfNotification: Array<BusTrackerNotification>) {
    @SuppressLint("SuspiciousIndentation")
    override fun toString(): String {
        val toReturn = "listSize " + listOfNotification.size + "\n"
                listOfNotification.forEach {
                    toReturn + it.toString() + "\n"
                }
        return toReturn
    }
}