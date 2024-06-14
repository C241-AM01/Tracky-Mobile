package com.megalogic.tracky.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.megalogic.tracky.data.model.AssetResponse
import com.megalogic.tracky.databinding.ItemAssetBinding
import com.megalogic.tracky.utils.PriceFormat
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class AssetListAdapter(
    private val context: Context,
    private var assetList: List<AssetResponse>,
    private val onItemClick: (AssetResponse) -> Unit
) : RecyclerView.Adapter<AssetListAdapter.AssetViewHolder>(), Filterable {

    private var filteredAssetList = assetList

    inner class AssetViewHolder(private val binding: ItemAssetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(asset: AssetResponse) {
            binding.tvAssetName.text = asset.name
            binding.tvAssetDescription.text = asset.description

            Glide.with(context).load(asset.imageURL).into(binding.ivAssetImage)

            val depreciationValue = asset.depreciationValue.toInt()
            val originalPrice = asset.originalPrice.toInt()
            val purchaseDate = LocalDate.parse(asset.purchaseDate)
            val today = LocalDate.now()

            val elapsedTime = ChronoUnit.DAYS.between(purchaseDate, today).toDouble()
            val depreciationRate = when (asset.depreciationRate) {
                "daily" -> elapsedTime
                "weekly" -> elapsedTime / 7
                "monthly" -> elapsedTime / 30
                "yearly" -> elapsedTime / 365
                else -> 0.0
            }

            val finalPrice = originalPrice - (depreciationValue * depreciationRate)
            binding.tvAssetFinalPrice.text = PriceFormat.getFormattedPrice(finalPrice.toInt())
            binding.tvAssetOriginalPrice.apply {
                text = PriceFormat.getFormattedPrice(originalPrice)
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
            binding.tvDepreciationValue.text = PriceFormat.getFormattedPrice(depreciationValue)
            binding.tvAssetDepreciationPeriod.text = when (asset.depreciationRate) {
                "daily" -> "Days"
                "weekly" -> "Weeks"
                "monthly" -> "Months"
                "yearly" -> "Years"
                else -> ""
            }
            binding.tvAssetDepreciationTime.text = depreciationRate.toInt().toString()
            binding.tvDepreciationRate.text = when (asset.depreciationRate) {
                "daily" -> "Days"
                "weekly" -> "Weeks"
                "monthly" -> "Months"
                "yearly" -> "Years"
                else -> ""
            }
            binding.tvAssetPurchasedDate.text = asset.purchaseDate
            binding.tvTrackerId.text = asset.trackerId

            binding.root.setOnClickListener {
                onItemClick(asset)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
        val binding = ItemAssetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AssetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AssetViewHolder, position: Int) {
        holder.bind(filteredAssetList[position])
    }

    override fun getItemCount(): Int = filteredAssetList.size

    fun updateData(newAssets: List<AssetResponse>) {
        assetList = newAssets
        filteredAssetList = newAssets
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase() ?: ""
                val filteredList = if (query.isEmpty()) {
                    assetList
                } else {
                    assetList.filter { it.name.lowercase().contains(query) }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredAssetList = results?.values as List<AssetResponse>
                notifyDataSetChanged()
            }
        }
    }
}
