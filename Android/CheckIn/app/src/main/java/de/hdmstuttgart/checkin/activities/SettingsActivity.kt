package de.hdmstuttgart.checkin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import de.hdmstuttgart.checkin.R
import java.lang.Boolean.TRUE

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme_CheckIn)
        } else {
            setTheme(R.style.Theme_CheckIn)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val themeSwitch: SwitchCompat = findViewById(R.id.themeSwitch)
        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES) {
            themeSwitch.isChecked = TRUE
        }

        themeSwitch.setOnCheckedChangeListener { _, _ ->
            if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                reload()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                reload()
            }
        }

        // button to get back to the home screen
        findViewById<Button>(R.id.backButtonSettings).setOnClickListener {
            val homeIntent = Intent(this, HomeActivity::class.java)
            startActivity(homeIntent)
        }

    }
    private fun reload() {
        val reloadIntent = Intent(this, SettingsActivity::class.java)
        startActivity(reloadIntent)
    }
}