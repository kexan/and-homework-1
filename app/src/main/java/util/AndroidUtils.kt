package util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object AndroidUtils {

    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun formatNum(number: Long): String {
        val doubleNum = number.toDouble()
        when {
            (number >= 1_000_000) -> return String.format("%.1f", doubleNum / 1_000_000) + "M"
            (number >= 10_000) -> return String.format("%.0f", doubleNum / 1_000) + "K"
            (number >= 1_000) -> return String.format("%.1f", doubleNum / 1_000) + "K"
        }
        return "$number"
    }
}