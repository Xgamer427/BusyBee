package com.example.myapplication.data

data class BusTrackerUiState(
    val notificationArray: Array<BusTrackerNotification> = arrayOf<BusTrackerNotification>(),
    val currentSetupTitle: String? = null,
    val currentSetupStop: Stop? = null,
    val currentSetupBus: Bus? = null,
    val currentSetupDepartureTime: DepartureTime? = null,
    val currentSetupBuffertime: Int = 0,
    val currentSetupAdditionalTime: Int = 0,

)