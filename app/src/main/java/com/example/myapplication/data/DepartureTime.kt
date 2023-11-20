package com.example.myapplication.data

data class DepartureTime(
    val hour: Int = 0,
    val min: Int = 0
): Comparable<DepartureTime>{
    init {
        if(hour<0 || hour>23 || min<0 || min>59){
            throw Exception("Values are not in time boundaries ${hour}, ${min}")
        }

    }
    override fun toString(): String {
        var hourStr = "$hour"
        if(hour<10){
            hourStr = "0" + hourStr
        }
        var minStr = "$min"
        if(min<10){
            minStr = "0" + minStr
        }
        return "$hourStr:$minStr"
    }

    fun plusMinutes(plusMin: Int): DepartureTime{

        val newMin: Int = min + plusMin
        val minForNewTime: Int = newMin%60
        val timeForNewHours: DepartureTime = plusHours(newMin/60)
        return DepartureTime(timeForNewHours.hour, minForNewTime)
    }

    fun plusHours(plusH: Int) : DepartureTime{
        val newhour = (hour+plusH)%24
        return DepartureTime(newhour, this.min)
    }

    fun minusMinutes(minusMin: Int): DepartureTime {
        var newMin: Int = min - minusMin
        var newHours = this.hour

        if(newMin<0){
            newHours = minusHours(Math.abs(newMin / 60) +1).hour
            newMin = 60+((newMin)%60)
        }

        return DepartureTime(newHours, newMin)
    }

    private fun minusHours(minusH: Int): DepartureTime {
        var newH: Int = hour - minusH
        if(newH<0){
            newH = 24+((newH)%24)
        }

        return DepartureTime(newH,this.min)
    }

    fun clone(): DepartureTime{
        return DepartureTime(hour,min)
    }

    override fun compareTo(other: DepartureTime): Int {
        if (hour<other.hour){
            return -1
        }else{
            if(hour==other.hour){
                if(min<other.min){
                    return -1
                }else{
                    if(min==other.min) {
                        return 0
                    }else{
                        return 1
                    }
                }
            }else{
                return 1
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DepartureTime

        if (hour != other.hour) return false
        if (min != other.min) return false

        return true
    }

    override fun hashCode(): Int {
        var result = hour
        result = 31 * result + min
        return result
    }
}