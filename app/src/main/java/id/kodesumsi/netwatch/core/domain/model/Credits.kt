package id.kodesumsi.netwatch.core.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Credits(

    @field:SerializedName("cast")
    val cast: List<Cast?>? = null,

) : Parcelable
