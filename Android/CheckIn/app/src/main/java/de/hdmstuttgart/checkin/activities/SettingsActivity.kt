package de.hdmstuttgart.checkin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import de.hdmstuttgart.checkin.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme_CheckIn)
        } else {
            setTheme(R.style.Theme_CheckIn)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val themeSwitch: Switch = findViewById(R.id.themeSwitch)
        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES) {
            themeSwitch.isChecked
        }

        themeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                reload()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                reload()
            }
        }

//        themeSwitch.setOnCheckedChangeListener(object:CompoundButton.OnCheckedChangeListener() {
//            @Override
//            fun onCheckedChange(buttonView : CompoundButton, isChecked : Boolean) {
//                if (isChecked) {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                    reload()
//                } else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                    reload()
//                }
//            }
//        }
//        )



        // button to get back to the home screen
        findViewById<Button>(R.id.backButtonSettings).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

    }
    private fun reload() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
        finish()
    }
}