package trashissue.rebage.data.local.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.*

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromListToJson(value: List<Float>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun fromJsonToList(value: String): List<Float> {
        return gson.fromJson(value, Array<Float>::class.java).toList()
    }

    @TypeConverter
    fun fromNestedListToJson(value: List<List<Float>>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun fromJsonToNestedList(value: String): List<List<Float>> {
        return gson.fromJson(value, Array<Array<Float>>::class.java).map { it.toList() }
    }

    @TypeConverter
    fun fromDateToTimestamp(value: Date): Long {
        return value.time
    }

    @TypeConverter
    fun fromTimeStampToDate(value: Long): Date {
        return Date(value)
    }
}
