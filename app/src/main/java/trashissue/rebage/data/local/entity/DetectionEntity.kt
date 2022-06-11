package trashissue.rebage.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "detection")
data class DetectionEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val image: String,
    val label: String,
    @ColumnInfo(name = "bounding_boxes")
    val boundingBoxes: List<List<Float>>,
    val scores: List<Float>,
    val total: Int,
    @ColumnInfo(name = "created_at")
    val createdAt: Date
)
