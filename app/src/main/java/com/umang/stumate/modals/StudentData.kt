package com.umang.stumate.modals

import android.text.Editable
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class StudentData(
    var studentID: String? = "",
    var studentName: String? = "",
    var emailID: String? = "",
    var studentPhoneNumber: String? = "",
    var collegeName: String? = "",
    var graduationYear: String? = "",
    var studentDept: String? = "",
    var studentSection: String? = "",
    var provider: String? = ""
)