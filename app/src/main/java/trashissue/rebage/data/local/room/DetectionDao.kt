package trashissue.rebage.data.local.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import trashissue.rebage.data.local.entity.DetectionEntity

@Dao
interface DetectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDetection(detection: DetectionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDetections(detections: List<DetectionEntity>)

    @Query("SELECT * FROM detection ORDER BY created_at DESC")
    fun getDetections(): Flow<List<DetectionEntity>>

    @Delete
    suspend fun deleteDetection(detection: DetectionEntity)
}
