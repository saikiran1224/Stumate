package com.umang.stumate.modals

import com.google.firebase.database.PropertyName

data class FeedbackData(

    @PropertyName("studentName")
    val studentName: String = "",
    @PropertyName("studentEmail")
    val studentEmail: String = "",
    @PropertyName("feedback")
    val feedback: String = ""
)
