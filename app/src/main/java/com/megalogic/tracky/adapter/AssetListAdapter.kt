package com.megalogic.tracky.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.megalogic.tracky.databinding.ItemAssetBinding
import com.megalogic.tracky.utils.setImageFromUrl
import android.widget.Filter
import android.widget.Filterable
import com.megalogic.tracky.data.model.AssetsItem
import com.megalogic.tracky.utils.PriceFormat
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.Locale

class AssetListAdapter(
    private val context: Context,
    private var AssetsItems: List<AssetsItem>,
    private val onItemClick: (AssetsItem) -> Unit
) : RecyclerView.Adapter<AssetListAdapter.AssetViewHolder>(), Filterable {

    private var AssetsItemsFiltered: List<AssetsItem> = AssetsItems

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
        val binding = ItemAssetBinding.inflate(LayoutInflater.from(context), parent, false)
        return AssetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AssetViewHolder, position: Int) {
        val AssetsItem = AssetsItemsFiltered[position]
        holder.bind(AssetsItem)
    }

    override fun getItemCount(): Int = AssetsItemsFiltered.size

    inner class AssetViewHolder(private val binding: ItemAssetBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(AssetsItem: AssetsItem) {
            with(binding) {
                tvTrackerId.text = AssetsItem.trackerId.toString()
                tvName.text = AssetsItem.name
                AssetsItem.imageURL?.let { ivAssetImage.setImageFromUrl(context, it) }
                tvDescription.text = AssetsItem.description
                tvOriginalPrice.apply {
                    text = PriceFormat.getFormattedPrice(AssetsItem.originalPrice!!.toInt())
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
                tvPurchasedDate.text = DateTimeFormat.formatCustomDate(AssetsItem.purchaseDate!!)
                tvDepreciationRate.text = when (AssetsItem.depreciationRate) {
                    "daily" -> "Days"
                    "weekly" -> "Weeks"
                    "monthly" -> "Months"
                    "yearly" -> "Years"
                    else -> ""
                }

                tvDepreciationPeriod.text = when (AssetsItem.depreciationRate) {
                    "daily" -> "Days"
                    "weekly" -> "Weeks"
                    "monthly" -> "Months"
                    "yearly" -> "Years"
                    else -> ""
                }

                tvDepreciationValue.text = AssetsItem.depreciationValue?.let {
                    PriceFormat.getFormattedPrice(
                        it.toInt())
                }

                val purchaseDate = LocalDate.parse(AssetsItem.purchaseDate)
                val today = LocalDate.now()
                val elapsedTime = ChronoUnit.DAYS.between(purchaseDate, today)
                val depreciationRate = when (AssetsItem.depreciationRate) {
                    "daily" -> elapsedTime
                    "weekly" -> elapsedTime / 7
                    "monthly" -> elapsedTime / 30
                    "yearly" -> elapsedTime / 365
                    else -> 0
                }
                tvDepreciationTime.text = depreciationRate.toString()

                val depreciationValue = AssetsItem.depreciationValue?.toInt()
                val originalPrice = AssetsItem.originalPrice?.toInt()
                val currentPrice = originalPrice?.minus((depreciationValue?.times(depreciationRate)!!))


                if (currentPrice != null) {
                    tvCurrentPrice.text = PriceFormat.getFormattedPrice(currentPrice.toInt())
                }


                // Set item click listener
                root.setOnClickListener {
                    onItemClick(AssetsItem)
                }
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charString = charSequence?.toString()?.lowercase(Locale.getDefault()) ?: ""
                AssetsItemsFiltered = if (charString.isEmpty()) {
                    AssetsItems
                } else {
                    AssetsItems.filter {
                        it.name?.lowercase(Locale.getDefault())!!.contains(charString) ||
                                it.description?.lowercase(Locale.getDefault())!!.contains(charString)
                    }
                }
                return FilterResults().apply { values = AssetsItemsFiltered }
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults?) {
                AssetsItemsFiltered = filterResults?.values as List<AssetsItem>
                notifyDataSetChanged()
            }
        }
    }
}