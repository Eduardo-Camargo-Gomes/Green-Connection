package com.example.green_connect

import android.text.style.BackgroundColorSpan
import androidx.annotation.DrawableRes

data class Tip(
    val title : String,
    val subtitle : String,
    @DrawableRes val logo : Int,
    @DrawableRes val background : Int,
)

