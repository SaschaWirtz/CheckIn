package de.hdmstuttgart.checkin.activities

import android.nfc.NfcAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import de.hdmstuttgart.checkin.R

class HomeActivity : AppCompatActivity() {

    lateinit var nfcAdapter: NfcAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
//        nfcAdapter = android.nfc.NfcAdapter.getDefaultAdapter(this)

//        if(nfcAdapter == null){
//            Toast.makeText(this, "This device does not support NFC", Toast.LENGTH_SHORT).show()
//            finish()
//        }

    }
}