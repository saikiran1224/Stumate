package com.umang.stumate.modals

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class NotificationData (

    @PropertyName("notificationTitle")
    var notificationTitle: String? = "",
    @PropertyName("notificationDesc")
    var notificationDesc: String? = "",
    @PropertyName("studentName")
    var studentName: String? = ""
)