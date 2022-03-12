package id.kodesumsi.netwatch.core.data.source.network.response

data class BaseResponse<T>(
    val page: Int? = null,
    val results: T? = null,
    val total_pages: Int? = null,
    val total_results: Int? = null
)