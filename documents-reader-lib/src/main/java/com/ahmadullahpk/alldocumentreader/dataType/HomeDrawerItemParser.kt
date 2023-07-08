package com.ahmadullahpk.alldocumentreader.dataType

class HomeDrawerItemParser {
    val icon: Int
    val name: String
    var isNotificationPresent = false
        private set
    var tagName: String? = null

    constructor(name: String, icon: Int) {
        this.name = name
        this.icon = icon
    }

    constructor(name: String, icon: Int, notificationAvailable: Int) {
        var notificationPresent = false
        this.name = name
        this.icon = icon
        if (notificationAvailable > 0) {
            notificationPresent = true
        }
        isNotificationPresent = notificationPresent
    }
}