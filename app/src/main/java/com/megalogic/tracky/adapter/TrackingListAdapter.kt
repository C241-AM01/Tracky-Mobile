package com.megalogic.tracky.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.megalogic.tracky.R
import com.megalogic.tracky.data.location.TrackingResponse

class TrackingListAdapter(
    private val context: Context,
    private var trackingResponses: List<TrackingResponse>
) : RecyclerView.Adapter<TrackingListAdapter.TrackingViewHolder>() {

    private var trackingResponsesFiltered: List<TrackingResponse> = trackingResponses

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackingViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_history_tracking, parent, false)
        return TrackingViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackingViewHolder, position: Int) {
        val trackingResponse = trackingResponsesFiltered[position]
        holder.bind(trackingResponse)
    }

    override fun getItemCount(): Int {
        return trackingResponsesFiltered.size
    }

    inner class TrackingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateStamp: TextView = itemView.findViewById(R.id.tv_datestamp_history)
        private val timeStamp: TextView = itemView.findViewById(R.id.tv_timestamp_history)
        private val latitude: TextView = itemView.findViewById(R.id.tv_latitude_history)
        private val longitude: TextView = itemView.findViewById(R.id.tv_longitude_history)

        fun bind(trackingResponse: TrackingResponse) {
            dateStamp.text = DateTimeFormat.formatDate(trackingResponse.timestamp)
            timeStamp.text = DateTimeFormat.formatTime(trackingResponse.timestamp)
            latitude.text = "Lat. %.5f".format(trackingResponse.lat.toDouble())
            longitude.text = "Long. %.5f".format(trackingResponse.lon.toDouble())
        }
    }
}
