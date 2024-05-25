package com.megalogic.tracky.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.megalogic.tracky.R
import com.megalogic.tracky.data.Asset
import com.megalogic.tracky.utils.setImageFromUrl

class AssetListAdapter(private val context: Context, private val assetResponses: List<Asset>) :
    RecyclerView.Adapter<AssetListAdapter.AssetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_asset, parent, false)
        return AssetViewHolder(view)
    }

    override fun onBindViewHolder(holder: AssetViewHolder, position: Int) {
        val assetResponse = assetResponses[position]
        holder.bind(assetResponse)
    }

    override fun getItemCount(): Int {
        return assetResponses.size
    }

    inner class AssetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView: TextView = itemView.findViewById(R.id.tv_asset_title)
        private val assetImageView: ImageView = itemView.findViewById(R.id.iv_asset_image)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.tv_asset_description)
        private val priceTextView: TextView = itemView.findViewById(R.id.tv_asset_price)
        private val dateTextView: TextView = itemView.findViewById(R.id.tv_asset_date)

        fun bind(assetResponse: Asset) {
            titleTextView.text = assetResponse.title
            assetImageView.setImageFromUrl(context, assetResponse.image)
            descriptionTextView.text = assetResponse.description
            priceTextView.text = assetResponse.price.toString()
            dateTextView.text = assetResponse.date
        }
    }
}