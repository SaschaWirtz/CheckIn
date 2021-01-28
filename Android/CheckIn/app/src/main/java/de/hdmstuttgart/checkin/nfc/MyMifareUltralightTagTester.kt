package de.hdmstuttgart.checkin.nfc

import android.content.Intent
import android.nfc.*
import android.nfc.tech.Ndef
import android.util.Log
import android.widget.TextView
import java.lang.reflect.InvocationTargetException
import java.nio.charset.Charset

class MyMifareUltralightTagTester {

    fun writeTag(tag: Tag, data: NdefMessage): Boolean {
        //Getting the tag in Ndef format
        val currentTag: Ndef = Ndef.get(tag)
        try {
            //Opening the connection for accessing the NFC tag discovered
            currentTag.connect()
            currentTag.writeNdefMessage(data)
            //Closing the connection
            currentTag.close()
            return true
        }catch(e: TagLostException){
            Log.e("NfcWriter", "Tag lost connection")
        }catch(e: InvocationTargetException){
            Log.e("NfcWriter", "Tag write error")
        }catch(e: FormatException){
            Log.e("NfcWriter", "Ndef message is malformed $data")
        }
        //Returning false if an error occurred
        return false
    }

    fun readTag(intent: Intent, tag: Tag, tv: TextView): String {
        val currentTag: Ndef = Ndef.get(tag)
        //Opening the connection for reading the NFC tag
        currentTag.connect()
        //Getting all extra information from the NFC tag
        val messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
        if (messages != null) {
            //Mapping the data found for easy access
            messages.map { message -> message as NdefMessage }
            //Getting the records ( the stored data  ) from the NFC tag
            val record = (messages[0] as NdefMessage).records[0]
            //Accessing the actual data "payload" as Bytes
            val payload: ByteArray = record.payload
            //Closing the connection to that read intent "NFC tag"
            currentTag.close()
            //Encoding the payload ( actual data stored ) with the right charset ( default is UTF-8 )
            val data = payload.toString(Charset.defaultCharset())
            val text = "You checked into "+data
            tv.text = text
            //Returning the payload encoded so it will be stored in the database
            return data
        }
        //Returning error as the read data to see in the database something went wrong reading the NFC tag
        //We store the error too in the database to see also the timestamp when the tag was discovered to get knowledge which NFC tag it was
        return "Error"
    }
}

