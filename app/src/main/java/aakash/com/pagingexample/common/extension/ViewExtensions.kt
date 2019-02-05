package aakash.com.pagingexample.common.extension

import android.content.res.Resources
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar


fun View.snack(@StringRes msg: Int) {
    Snackbar.make(this, context.getString(msg), Snackbar.LENGTH_SHORT).show()
}

fun View.snack(msg: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, msg, duration).show()
}

fun Boolean.isVisible() = if (this) View.VISIBLE else View.GONE

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.inVisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.toDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

fun getSmallCardWidth(): Int {
    return ((Resources.getSystem().displayMetrics.widthPixels) / 1.8).toInt()
}

fun getBigCardWidth(): Int {
    return (((Resources.getSystem().displayMetrics.widthPixels) * 85) / 100)
}