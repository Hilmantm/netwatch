package id.kodesumsi.netwatch.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import id.kodesumsi.netwatch.R
import id.kodesumsi.netwatch.core.utils.Constant.Companion.SPLASHSCREEN_DELAY
import id.kodesumsi.netwatch.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        Handler(mainLooper).postDelayed({
            val intentToOnboarding = Intent(this, MainActivity::class.java)
            startActivity(intentToOnboarding)
            finish()
        }, SPLASHSCREEN_DELAY)

    }
}