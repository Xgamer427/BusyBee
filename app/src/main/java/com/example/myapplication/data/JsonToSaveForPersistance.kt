package com.example.myapplication.data

class JsonToSaveForPersistance(var listOfNotification: Array<BusTrackerNotification>) {
    override fun toString(): String {
        val toReturn = "listSize " + listOfNotification.size + "\n"
                listOfNotification.forEach {
                    toReturn + it.toString() + "\n"
                }
        return toReturn
    }
}