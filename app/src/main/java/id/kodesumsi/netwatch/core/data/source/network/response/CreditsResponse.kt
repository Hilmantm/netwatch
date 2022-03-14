package id.kodesumsi.netwatch.core.data.source.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class CreditsResponse(

    @field:SerializedName("cast")
    val cast: List<CastResponse>? = listOf(),

)