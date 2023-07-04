package com.mostafa.quran.utils

import android.content.Context
import android.graphics.Typeface

object FontUtils {

    fun getAllFonts(context: Context): List<Typeface> {
        val fontManager = context.assets
        val fontFiles =
            fontManager.list("fonts") // Assuming your fonts are stored in the "fonts" directory in the assets folder

        val fontList = mutableListOf<Typeface>()
        fontFiles?.forEach { fontFile ->
            val fontPath = "fonts/$fontFile"
            val typeface = Typeface.createFromAsset(fontManager, fontPath)
            fontList.add(typeface)
        }

        return fontList
    }
}