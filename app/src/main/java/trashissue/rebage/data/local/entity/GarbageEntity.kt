package trashissue.rebage.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GarbageEntity(
    @PrimaryKey
    val id: Int
)
