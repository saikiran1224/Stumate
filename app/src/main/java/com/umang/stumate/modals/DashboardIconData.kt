package com.umang.stumate.modals

import android.graphics.drawable.Drawable
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import java.io.Serializable

@IgnoreExtraProperties
data class DashboardIconData (

    @PropertyName("iconActionName")
    var iconActionName: String? = "",

    @PropertyName("iconDrawableName")
    var iconDrawableName: Int

)

