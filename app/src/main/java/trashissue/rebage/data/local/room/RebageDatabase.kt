package trashissue.rebage.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import trashissue.rebage.data.local.entity.DetectionEntity

@Database(
    version = 1,
    entities = [DetectionEntity::class],
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RebageDatabase : RoomDatabase() {

    abstract fun getDetectionDao(): DetectionDao
}
