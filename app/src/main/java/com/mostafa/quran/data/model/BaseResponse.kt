package com.mostafa.quran.data.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
open class BaseResponse(
    val code: Int? = null,
    val status: String = "",
    val data: String = ""
): Serializable