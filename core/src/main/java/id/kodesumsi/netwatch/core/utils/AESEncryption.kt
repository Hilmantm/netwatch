package id.kodesumsi.netwatch.core.utils

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AESEncryption{
    fun encrypt(data: String, secretKey: SecretKey, iv: ByteArray): String{
        val cipher: Cipher = Cipher.getInstance("AES")
        val secretKeySpec: SecretKeySpec = SecretKeySpec(secretKey.encoded, "AES")
        val ivSpec = IvParameterSpec(iv)
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec)
        val cipherText: ByteArray = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(cipherText, Base64.DEFAULT)
    }

    fun decrypt(encryptedText: String, secretKey: SecretKey, iv: ByteArray): String{
        val cipherText = Base64.decode(encryptedText, Base64.DEFAULT)
        val cipher: Cipher = Cipher.getInstance("AES")
        val secretKeySpec: SecretKeySpec = SecretKeySpec(secretKey.encoded, "AES")
        val ivSpec = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec)
        return String(cipher.doFinal(cipherText))
    }
}