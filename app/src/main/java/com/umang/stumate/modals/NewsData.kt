package com.umang.stumate.modals

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class NewsData (
    var newsPhoto: Int,
    var newsTitle: String? = null,
    var newsDescription: String? = null
)