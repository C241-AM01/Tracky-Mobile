package com.megalogic.tracky.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.megalogic.tracky.data.asset.AssetResponse
import com.megalogic.tracky.databinding.ActivityDetailBinding
import com.megalogic.tracky.utils.setImageFromUrl
import com.megalogic.tracky.utils.PriceFormat

class DetailAdapter(
    private val context: Context,
    private val assetResponse: AssetResponse
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
                tvAssetPurchasedDate.text = DateTimeFormat.formatCustomDate(assetResponse.date)
            }
        }
    }
}
