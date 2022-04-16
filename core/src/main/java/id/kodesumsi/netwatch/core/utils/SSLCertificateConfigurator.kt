package id.kodesumsi.netwatch.core.utils

import android.content.Context
import id.kodesumsi.core.R
import java.io.IOException
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.cert.Certificate
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

object SSLCertificateConfigurator {

    @Throws(
        CertificateException::class,
        IOException::class,
        KeyStoreException::class,
        NoSuchElementException::class,
        KeyManagementException::class
    )

    fun getSSLConfigure(context: Context): SSLContext {
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, getTrustManager(context).trustManagers, null)
        return sslContext
    }

    fun getTrustManager(context: Context): TrustManagerFactory {
        val trustManagerFactoryAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
        val trustManagerFactory = TrustManagerFactory.getInstance(trustManagerFactoryAlgorithm)
        trustManagerFactory.init(getKeyStore(context))
        return trustManagerFactory
    }

    fun getKeyStore(context: Context): KeyStore {
        val keyStoreType = KeyStore.getDefaultType()
        val keyStore = KeyStore.getInstance(keyStoreType)
        keyStore.load(null, null)
        keyStore.setCertificateEntry("ca", getCertificate(context))
        return keyStore
    }

    fun getCertificate(context: Context): Certificate? {
        val certificateFactory: CertificateFactory? = CertificateFactory.getInstance("X.509")
        return context.resources.openRawResource(R.raw.themoviedbcert).use { cert ->
            certificateFactory?.generateCertificate(cert)
        }
    }

}