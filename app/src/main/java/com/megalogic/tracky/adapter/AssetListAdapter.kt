package com.megalogic.tracky.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.megalogic.tracky.data.asset.AssetResponse
import com.megalogic.tracky.databinding.ItemAssetBinding
import com.megalogic.tracky.utils.setImageFromUrl
import android.widget.Filter
import android.widget.Filterable
import com.megalogic.tracky.utils.PriceFormat
import java.util.Locale

class AssetListAdapter(
    private val context: Context,
    private var assetResponses: List<AssetResponse>
) : RecyclerView.Adapter<AssetListAdapter.AssetViewHolder>(), Filterable {

    private var assetResponsesFiltered: List<AssetResponse> = assetResponses

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
        val binding = ItemAssetBinding.inflate(LayoutInflater.from(context), parent, false)
        return AssetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AssetViewHolder, position: Int) {
        val assetResponse = assetResponsesFiltered[position]
        holder.bind(assetResponse)
    }

    override fun getItemCount(): Int {
        return assetResponsesFiltered.size
    }

    inner class AssetViewHolder(private val binding: ItemAssetBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(assetResponse: AssetResponse) {
            with(binding) {
                tvTrackerId.text = assetResponse.trackerId.toString()
                tvAssetTitle.text = assetResponse.title
                ivAssetImage.setImageFromUrl(context, assetResponse.image)
                tvAssetDescription.text = assetResponse.description
                tvAssetInitialPrice.apply {
                    text = PriceFormat.getFormattedPrice(assetResponse.initialPrice)
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
                tvAssetFinalPrice.text = PriceFormat.getFormattedPrice(assetResponse.finalPrice)
                tvAssetPurchasedDate.text = assetResponse.date
                tvDepreciation.text = PriceFormat.getFormattedDepreciation(assetResponse.depreciation)
            }
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
