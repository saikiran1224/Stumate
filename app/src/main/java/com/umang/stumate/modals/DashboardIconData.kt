package com.umang.stumate.modals

import android.graphics.drawable.Drawable
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class DashboardIconData (
    var iconActionName: String? = "",
    var iconDrawableName: Int

)

