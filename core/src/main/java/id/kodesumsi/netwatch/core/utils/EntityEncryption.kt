package id.kodesumsi.netwatch.core.utils

import id.kodesumsi.netwatch.core.data.source.local.entity.MovieEntity
import java.security.SecureRandom
import javax.crypto.SecretKey

object EntityEncryption {

    fun encryptMovieEntity(movie: MovieEntity, secretKey: SecretKey, secureRandom: SecureRandom): MovieEntity {
        return MovieEntity(
            id = AESEncryption.encrypt(data = movie.id, secretKey = secretKey, iv = secureRandom.generateSeed(16)),
            overview = AESEncryption.encrypt(data = movie.overview.toString(), secretKey = secretKey, iv = secureRandom.generateSeed(16)),
            title = AESEncryption.encrypt(data = movie.title.toString(), secretKey = secretKey, iv = secureRandom.generateSeed(16)),
            posterPath = AESEncryption.encrypt(data = movie.posterPath.toString(), secretKey = secretKey, iv = secureRandom.generateSeed(16)),
            backdropPath = AESEncryption.encrypt(data = movie.backdropPath.toString(), secretKey = secretKey, iv = secureRandom.generateSeed(16)),
            releaseDate = AESEncryption.encrypt(data = movie.releaseDate.toString(), secretKey = secretKey, iv = secureRandom.generateSeed(16)),
            voteAverage = AESEncryption.encrypt(data = movie.voteAverage.toString(), secretKey = secretKey, iv = secureRandom.generateSeed(16)),
        )
    }

    fun decryptMovieEntity(movie: MovieEntity, secretKey: SecretKey, secureRandom: SecureRandom): MovieEntity {
        return MovieEntity(
            id = AESEncryption.decrypt(encryptedText = movie.id, secretKey = secretKey, iv = secureRandom.generateSeed(16)),
            overview = AESEncryption.decrypt(encryptedText = movie.overview.toString(), secretKey = secretKey, iv = secureRandom.generateSeed(16)),
            title = AESEncryption.decrypt(encryptedText = movie.title.toString(), secretKey = secretKey, iv = secureRandom.generateSeed(16)),
            posterPath = AESEncryption.decrypt(encryptedText = movie.posterPath.toString(), secretKey = secretKey, iv = secureRandom.generateSeed(16)),
            backdropPath = AESEncryption.decrypt(encryptedText = movie.backdropPath.toString(), secretKey = secretKey, iv = secureRandom.generateSeed(16)),
            releaseDate = AESEncryption.decrypt(encryptedText = movie.releaseDate.toString(), secretKey = secretKey, iv = secureRandom.generateSeed(16)),
            voteAverage = AESEncryption.decrypt(encryptedText = movie.voteAverage.toString(), secretKey = secretKey, iv = secureRandom.generateSeed(16)),
        )
    }

}