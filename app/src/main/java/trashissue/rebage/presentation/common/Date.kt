package trashissue.rebage.presentation.common

import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {

    private var formatter = SimpleDateFormat("E, dd MMM yyyy HH:mm", Locale.getDefault())

    fun format(date: Date): String {
        return formatter.format(date)
    }
}
