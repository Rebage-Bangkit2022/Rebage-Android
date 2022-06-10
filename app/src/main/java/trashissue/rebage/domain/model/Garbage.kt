package trashissue.rebage.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Garbage(
    val id: Int,
    val name: String,
    val price: Int,
    val image: String
) : Parcelable
