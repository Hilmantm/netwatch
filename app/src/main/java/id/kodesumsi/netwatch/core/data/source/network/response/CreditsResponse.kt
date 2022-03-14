package id.kodesumsi.netwatch.core.data.source.network.response

import com.google.gson.annotations.SerializedName

data class CreditsResponse(

    @field:SerializedName("cast")
    val cast: List<CastResponse>? = listOf(),

)