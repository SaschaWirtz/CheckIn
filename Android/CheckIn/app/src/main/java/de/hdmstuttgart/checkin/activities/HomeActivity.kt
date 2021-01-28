package de.hdmstuttgart.checkin.activities

import android.content.Intent
import android.app.PendingIntent
import android.content.IntentFilter
import android.icu.util.Calendar
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.NfcF
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.room.Room
import de.hdmstuttgart.checkin.R
import de.hdmstuttgart.checkin.db.CheckInDao
import de.hdmstuttgart.checkin.db.CheckInDatabase
import de.hdmstuttgart.checkin.db.CheckInEntity
import de.hdmstuttgart.checkin.nfc.MyMifareUltralightTagTester
import java.lang.NullPointerException
import java.util.*

class HomeActivity : AppCompatActivity() {

    //Setting nfc action
    private val doWrite = false

    private lateinit var adapter: NfcAdapter
    private lateinit var myMifareRW: MyMifareUltralightTagTester
    private lateinit var resultText: TextView
    private lateinit var checkInDao: CheckInDao

    lateinit var intentFiltersArray: Array<IntentFilter>
    lateinit var pendingIntent: PendingIntent
    private val techListsArray = arrayOf(arrayOf<String>(NfcF::class.java.name))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        findViewById<ImageView>(R.id.statisticsButton).setOnClickListener {
            val intent = Intent(this, StatisticsActivity::class.java)
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.settingsButton).setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        try{
            adapter = android.nfc.NfcAdapter.getDefaultAdapter(this)
        }catch(e: NullPointerException){
            Toast.makeText(this,"NFC disabled on this device.", Toast.LENGTH_SHORT).show()
            finish()
        }

        initViews()

        myMifareRW = MyMifareUltralightTagTester()
        adapter = NfcAdapter.getDefaultAdapter(this)

        //Prevent default nfc discover page from android to appear
        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val filters = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED).apply {
            try {
                addDataType("*/*")
            } catch (e: IntentFilter.MalformedMimeTypeException) {
                throw RuntimeException("fail", e)
            }
        }
        intentFiltersArray = arrayOf(filters)
    }

    private fun initViews(){
        resultText = findViewById(R.id.textView)
    }

    public override fun onPause() {
        super.onPause()
        adapter.disableForegroundDispatch(this)
    }

    public override fun onResume() {
        super.onResume()
        adapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        var tagFromIntent: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
        if(tagFromIntent != null){
            //Since this is only a base to work with you need to hardcode if you want to read or write
            if (doWrite) {
                val data = "SomeData"
                val defRecord: NdefRecord = NdefRecord.createMime(data, data.toByteArray())
                val defMessage = NdefMessage(arrayOf(defRecord))
                myMifareRW.writeTag(tagFromIntent, defMessage)
                Toast.makeText(this, "write successful", Toast.LENGTH_SHORT).show()
            }
            else{
                myMifareRW.readTag(intent ,tagFromIntent, resultText)
                Thread(Runnable {

                    checkInDao = Room.databaseBuilder(this, CheckInDatabase::class.java, "checkIn_database").allowMainThreadQueries().build().checkInDao()

                    this.runOnUiThread(Runnable {
                        insertDataToDatabase(resultText.text.toString())
                    })

                }).run()
            }
        }
    }

    fun insertDataToDatabase(nfcTagData: String){
        val current = Calendar.getInstance().time
        val currentDateString = current.toLocaleString()
        val newEntity = CheckInEntity(currentDateString, nfcTagData)
        checkInDao.addCheckIn(newEntity)
    }

}