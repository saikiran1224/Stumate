package com.umang.stumate.modals

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class NotificationData (

    var notificationTitle: String? = "",
    var notificationDesc: String? = "",
    var studentName: String? = ""
)