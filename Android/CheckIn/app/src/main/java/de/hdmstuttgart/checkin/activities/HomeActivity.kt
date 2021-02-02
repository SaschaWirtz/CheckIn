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

class HomeActivity : AppCompatActivity() {

    //Setting nfc action
    private val doWrite = false
    private val roomName = "Room001"
    //Variable that can deny the execution of a write, to prevent duplicated rooms
    private var doAgain = true

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

        // giving the buttons the intent they needed to switch activities
        findViewById<ImageView>(R.id.statisticsButton).setOnClickListener {
            val intent = Intent(this, StatisticsActivity::class.java)
            startActivity(intent)
        }
        findViewById<ImageView>(R.id.settingsButton).setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        findViewById<TextView>(R.id.nfc_info_text).setOnClickListener {
            findViewById<TextView>(R.id.nfc_info_text).setText(R.string.home_center_textview_preset)
        }

        //Checking if the devices has NFC and if its enabled
        try{
            adapter = android.nfc.NfcAdapter.getDefaultAdapter(this)
        }catch(e: NullPointerException){
            Toast.makeText(this,"NFC disabled on this device.", Toast.LENGTH_SHORT).show()
            finish()
        }

        initViews()

        //Init the adapters
        myMifareRW = MyMifareUltralightTagTester()
        adapter = NfcAdapter.getDefaultAdapter(this)
        checkInDao = Room.databaseBuilder(this, CheckInDatabase::class.java, "checkIn_database").allowMainThreadQueries().build().checkInDao()

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
        resultText = findViewById(R.id.nfc_info_text)
    }

    public override fun onPause() {
        super.onPause()
        //Prioritize the current activity
        adapter.disableForegroundDispatch(this)
    }

    public override fun onResume() {
        super.onResume()
        adapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray)
    }

    //When a NFC tag is discovered
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val tagFromIntent: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
        if(tagFromIntent != null){
            //Since this is only a base to work with you need to hardcode if you want to read or write
            if (doWrite && doAgain) {
                val data = roomName
                //Writing the data as simple as possible onto the NFC tags as Bytes
                val defRecord: NdefRecord = NdefRecord.createMime(data, data.toByteArray())
                val defMessage = NdefMessage(arrayOf(defRecord))
                if(myMifareRW.writeTag(tagFromIntent, defMessage)){
                    Toast.makeText(this, "write successful", Toast.LENGTH_SHORT).show()
                    //After successfully writing onto the NFC tag the next discovered NFC tag will be only scanned to hinder duplicates
                    doAgain = false
                }else{
                    Toast.makeText(this, "write unsuccessful", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                //Read out the data stored on the NFC tag
                val data = myMifareRW.readTag(intent ,tagFromIntent, resultText)
                //Run database query's on a different thread to keep the programming running fluent
                Thread(Runnable {

                    this.runOnUiThread(Runnable {
                        //Write the data into the database
                        insertDataToDatabase(data)
                    })

                }).run()
            }
        }
    }

    private fun insertDataToDatabase(nfcTagData: String){
        //Get current date and time for the accesses time when the NFC tag was discovered
        val current = Calendar.getInstance().time
        //Using a old function that is deprecated because our test device was running api level 24
        val currentDateString = current.toLocaleString()
        val newEntity = CheckInEntity(currentDateString, nfcTagData)
        //Adding the created entity to the database via room
        checkInDao.addCheckIn(newEntity)
    }
}