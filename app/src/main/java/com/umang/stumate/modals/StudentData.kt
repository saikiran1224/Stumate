package com.umang.stumate.modals

import android.text.Editable
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class StudentData(

    @PropertyName("studentID")
    var studentID: String? = "",
    @PropertyName("studentName")
    var studentName: String? = "",
    @PropertyName("emailID")
    var emailID: String? = "",
    @PropertyName("studentPhoneNumber")
    var studentPhoneNumber: String? = "",
    @PropertyName("collegeName")
    var collegeName: String? = "",
    @PropertyName("graduationYear")
    var graduationYear: String? = "",
    @PropertyName("studentDept")
    var studentDept: String? = "",
    @PropertyName("studentSection")
    var studentSection: String? = "",
    @PropertyName("provider")
    var provider: String? = ""
)