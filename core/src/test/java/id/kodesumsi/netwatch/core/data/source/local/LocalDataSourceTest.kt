package id.kodesumsi.netwatch.core.data.source.local

import id.kodesumsi.netwatch.core.data.source.local.room.MovieDao
import id.kodesumsi.netwatch.core.data.source.local.room.NetwatchDatabase
import junit.framework.TestCase

class LocalDataSourceTest : TestCase() {

    private lateinit var movieDao: MovieDao
    private lateinit var db: NetwatchDatabase

    public override fun setUp() {

        super.setUp()
    }

    public override fun tearDown() {}

    fun testGetAllFavoriteMovies() {}

    fun testIsFavorite() {}

    fun testInsertFavoriteMovie() {}

    fun testRemoveFavoriteMovie() {}
}