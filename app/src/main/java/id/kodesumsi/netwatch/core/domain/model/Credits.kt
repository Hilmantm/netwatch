package id.kodesumsi.netwatch.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Credits(
    val cast: List<Cast> = listOf(),
) : Parcelable
