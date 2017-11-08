package uk.co.tomek.openweatherkt.extensions

import android.app.Activity
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

/**
 * Android Activity extensions.
 */
fun Activity.hideKeyboard() {
    val currentFocus = currentFocus
    if (currentFocus != null) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}

fun Activity.showKeyboard() {
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
}