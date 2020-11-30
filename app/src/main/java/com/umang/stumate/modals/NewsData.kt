package com.umang.stumate.modals

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

@IgnoreExtraProperties
data class NewsData (
    @PropertyName("newsPhoto")
    var newsPhoto: Int,
    @PropertyName("newsTitle")
    var newsTitle: String? = null,
    @PropertyName("newsDescription")
    var newsDescription: String? = null
)