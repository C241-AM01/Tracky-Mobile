package com.megalogic.tracky.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.megalogic.tracky.data.model.AssetsItem
import com.megalogic.tracky.databinding.ActivityDetailBinding
import com.megalogic.tracky.utils.setImageFromUrl
import com.megalogic.tracky.utils.PriceFormat

class DetailAdapter(
    private val context: Context,
    private val assetResponse: AssetsItem
) : RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val binding = ActivityDetailBinding.inflate(LayoutInflater.from(context), parent, false)
        return DetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(assetResponse)
    }

    override fun getItemCount(): Int = 1

    inner class DetailViewHolder(private val binding: ActivityDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(assetResponse: AssetsItem) {
            with(binding) {
                tvTrackerId.text = assetResponse.trackerId.toString()
                tvName.text = assetResponse.name
                assetResponse.imageURL?.let { ivAssetImage.setImageFromUrl(context, it) }
                tvDescription.text = assetResponse.description
                tvOriginalPrice.apply {
                    text = PriceFormat.getFormattedPrice(assetResponse.originalPrice!!.toInt())
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
                tvCurrentPrice.text = PriceFormat.getFormattedPrice(assetResponse.currentPrice!!.toInt())
                tvPurchasedDate.text = DateTimeFormat.formatCustomDate(assetResponse.purchaseDate!!)
                tvDepreciationRate.text = when (assetResponse.depreciationRate) {
                    "daily" -> "Days"
                    "weekly" -> "Weeks"
                    "monthly" -> "Months"
                    "yearly" -> "Years"
                    else -> ""
                }

                tvDepreciationPeriod.text = when (assetResponse.depreciationRate) {
                    "daily" -> "Days"
                    "weekly" -> "Weeks"
                    "monthly" -> "Months"
                    "yearly" -> "Years"
                    else -> ""
                }

                tvDepreciationValue.text = assetResponse.depreciationValue?.let {
                    PriceFormat.getFormattedPrice(
                        it.toInt())
                }
            }
        }
    }
}
