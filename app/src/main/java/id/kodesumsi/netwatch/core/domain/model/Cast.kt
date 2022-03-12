package id.kodesumsi.netwatch.core.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cast(
	val castId: Int? = null,
	val character: String? = null,
	val gender: Int? = null,
	val creditId: String? = null,
	val knownForDepartment: String? = null,
	val originalName: String? = null,
	val popularity: Double? = null,
	val name: String? = null,
	val profilePath: String? = null,
	val id: Int? = null,
	val adult: Boolean? = null,
	val order: Int? = null,
	val cast: List<Cast?>? = null
) : Parcelable
