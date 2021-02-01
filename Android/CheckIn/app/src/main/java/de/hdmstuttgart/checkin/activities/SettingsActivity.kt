package de.hdmstuttgart.checkin.activities

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import de.hdmstuttgart.checkin.R
import java.lang.Boolean.TRUE

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val themeSwitch: SwitchCompat = findViewById(R.id.themeSwitch)
        val isNightTheme = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        // controlls if the switch is turned on or turn off
        when (isNightTheme) {
            Configuration.UI_MODE_NIGHT_YES ->
               themeSwitch.isChecked = TRUE
        }

        // sets the UI_MODE to night or day theme if the switch was pressed
        themeSwitch.setOnCheckedChangeListener{ _, _ ->
            val isNightTheme = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            when (isNightTheme) {
                Configuration.UI_MODE_NIGHT_YES ->
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Configuration.UI_MODE_NIGHT_NO ->
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }
}