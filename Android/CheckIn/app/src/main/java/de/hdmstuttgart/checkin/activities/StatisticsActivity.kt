package de.hdmstuttgart.checkin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import de.hdmstuttgart.checkin.R
import de.hdmstuttgart.checkin.db.*

class StatisticsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var checkInDao: CheckInDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        recyclerView = findViewById(R.id.history)
        recyclerView.layoutManager = LinearLayoutManager(baseContext)
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))

        checkInDao = Room.databaseBuilder(this, CheckInDatabase::class.java, "checkIn_database").allowMainThreadQueries().build().checkInDao()

        recyclerView.adapter = MyAdapter(checkInDao.readAllData())

        findViewById<Button>(R.id.deleteButton).setOnClickListener {
            checkInDao.nukeTable()
            recyclerView.adapter = MyAdapter(checkInDao.readAllData())
        }

        findViewById<Button>(R.id.backButtonStatistics).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        recyclerView.adapter = MyAdapter(checkInDao.readAllData())
    }
}