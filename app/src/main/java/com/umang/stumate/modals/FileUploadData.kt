package com.umang.stumate.modals

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class FileUploadData (
    var fileName: String? = "",
    var subjectName: String? = "",
    var unitNumber: String? = "",
    var fileType: String? = "",
    var fileURL: String? = "",
    var studentName: String? = "",
    var dateOfPublishing: String? = ""
)