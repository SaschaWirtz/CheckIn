package de.hdmstuttgart.checkin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import de.hdmstuttgart.checkin.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //Waiting for the rest of the app to finish loading
        val runnable: Runnable = Runnable {
            val intent = Intent(this,HomeActivity::class.java)
            finish()
            startActivity(intent)
        }
        Thread(runnable).start()
    }
}