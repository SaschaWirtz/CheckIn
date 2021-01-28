package de.hdmstuttgart.checkin.activities

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import de.hdmstuttgart.checkin.R
import de.hdmstuttgart.checkin.db.CheckInDao
import de.hdmstuttgart.checkin.db.CheckInEntity

class CustomView : ConstraintLayout {

    // setting the values for every textview
    var checkIn: CheckInEntity? = null
        set(checkIn: CheckInEntity?) {
            field = checkIn
            findViewById<TextView>(R.id.room).text = field!!.NfcTag
            findViewById<TextView>(R.id.date).text = field!!.CheckInDate.split(" ") [0]            // date and time came in one String they needed to be split
            findViewById<TextView>(R.id.time).text = field!!.CheckInDate.split(" ") [1]
        }

    // override all constructors to ensure custom logic runs in all cases
    constructor(context: Context): this(context, null)

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int): super(context, attrs, defStyleAttr, defStyleRes) {
        // put all custom logic in this constructor, which always runs
        inflate(getContext(), R.layout.recycler_view_item, this)
    }
}