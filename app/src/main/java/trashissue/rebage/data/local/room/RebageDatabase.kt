package trashissue.rebage.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import trashissue.rebage.data.local.entity.GarbageEntity

@Database(
    version = 1,
    entities = [GarbageEntity::class],
    exportSchema = false
)
abstract class RebageDatabase : RoomDatabase() {
}