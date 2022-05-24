package trashissue.rebage.presentation.common.component

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

@Suppress("NOTHING_TO_INLINE")
inline fun toast(context: Context, @StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, context.getString(message), duration).show()
}

@Suppress("NOTHING_TO_INLINE")
@JvmName("toast1")
inline fun Context.toast(@StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) {
    toast(this, message, duration)
}
