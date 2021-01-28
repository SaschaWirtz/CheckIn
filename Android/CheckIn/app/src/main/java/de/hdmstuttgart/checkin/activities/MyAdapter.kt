package de.hdmstuttgart.checkin.activities

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.hdmstuttgart.checkin.db.CheckInEntity

class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder> {
    // no Context reference needed—can get it from a ViewGroup parameter
    private var checkInData: List<CheckInEntity>

    constructor(checkInData: List<CheckInEntity>) {
        // make own copy of the list so it can't be edited externally
        this.checkInData = ArrayList<CheckInEntity>(checkInData)
    }

    override fun getItemCount(): Int {
        return checkInData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // no need for a LayoutInflater instance
        // the custom view inflates itself
        val itemView: CustomView  = CustomView(parent.context)
        // manually set the CustomView's size
        itemView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.customView.checkIn = checkInData[position]
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var customView: CustomView = v as CustomView
            private set
    }
}