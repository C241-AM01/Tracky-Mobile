package com.megalogic.tracky.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.megalogic.tracky.R
import com.megalogic.tracky.data.asset.AssetResponse
import com.megalogic.tracky.utils.setImageFromUrl
import android.widget.Filter
import android.widget.Filterable
import java.util.Locale

class AssetListAdapter(
    private val context: Context,
    private var assetResponses: List<AssetResponse>
) : RecyclerView.Adapter<AssetListAdapter.AssetViewHolder>(), Filterable {

    private var assetResponsesFiltered: List<AssetResponse> = assetResponses

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_asset, parent, false)
        return AssetViewHolder(view)
    }

    override fun onBindViewHolder(holder: AssetViewHolder, position: Int) {
        val assetResponse = assetResponsesFiltered[position]
        holder.bind(assetResponse)
    }

    override fun getItemCount(): Int {
        return assetResponsesFiltered.size
    }

    inner class AssetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView: TextView = itemView.findViewById(R.id.tv_asset_title)
        private val assetImageView: ImageView = itemView.findViewById(R.id.iv_asset_image)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.tv_asset_description)
        private val priceTextView: TextView = itemView.findViewById(R.id.tv_asset_price)
        private val dateTextView: TextView = itemView.findViewById(R.id.tv_asset_date)

        fun bind(assetResponse: AssetResponse) {
            titleTextView.text = assetResponse.title
            assetImageView.setImageFromUrl(context, assetResponse.image)
            descriptionTextView.text = assetResponse.description
            priceTextView.text = assetResponse.price.toString()
            dateTextView.text = assetResponse.date
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charString = charSequence?.toString()?.lowercase(Locale.getDefault()) ?: ""
                assetResponsesFiltered = if (charString.isEmpty()) {
                    assetResponses
                } else {
                    assetResponses.filter {
                        it.title.lowercase(Locale.getDefault()).contains(charString) ||
                                it.description.lowercase(Locale.getDefault()).contains(charString)
                    }
                }
                return FilterResults().apply { values = assetResponsesFiltered }
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults?) {
                assetResponsesFiltered = filterResults?.values as List<AssetResponse>
                notifyDataSetChanged()
            }
        }
    }
}
