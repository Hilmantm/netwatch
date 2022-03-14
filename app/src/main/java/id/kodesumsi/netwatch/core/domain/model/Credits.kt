package id.kodesumsi.netwatch.core.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Credits(
    val cast: List<Cast> = listOf(),
) : Parcelable
