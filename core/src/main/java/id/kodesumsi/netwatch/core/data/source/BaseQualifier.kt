package id.kodesumsi.netwatch.core.data.source.network

import javax.inject.Qualifier

/**
 * Class ini digunakan untuk menklasifikasikan provides tipe data yang sama
 * pada hilt
 *
 * */

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BASE_URL

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class API_KEY

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DB_KEY