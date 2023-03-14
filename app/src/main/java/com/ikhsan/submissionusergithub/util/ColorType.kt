package com.ikhsan.submissionusergithub.util

import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.ikhsan.submissionusergithub.R

object ColorType {
    fun TextView?.setColor(context: Context, type: String?) {
        when (type) {
            "User" -> this?.apply {
                setTextColor(ContextCompat.getColor(context, R.color.green))
                text = type
            }
            "Organization" -> this?.apply {
                setTextColor(ContextCompat.getColor(context, R.color.red))
                text = type
            }
        }
    }
}