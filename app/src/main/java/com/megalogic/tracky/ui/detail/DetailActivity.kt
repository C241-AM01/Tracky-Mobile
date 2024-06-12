package com.megalogic.tracky.ui.detail

import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.megalogic.tracky.data.asset.AssetResponse
import com.megalogic.tracky.databinding.ActivityDetailBinding
import com.megalogic.tracky.utils.PriceFormat
import com.megalogic.tracky.utils.setImageFromUrl

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val assetResponse: AssetResponse
//        assetResponse = intent.getParcelableExtra("asset_response")
//
//        assetResponse?.let {
//            bindData(it)
//        }
    }

//    private fun bindData(assetResponse: AssetResponse) {
//        with(binding) {
//            tvTrackerId.text = assetResponse.trackerId.toString()
//            tvAssetTitle.text = assetResponse.title
//            ivAssetImage.setImageFromUrl(this@DetailActivity, assetResponse.image)
//            tvAssetDescription.text = assetResponse.description
//            tvAssetInitialPrice.apply {
//                text = PriceFormat.getFormattedPrice(assetResponse.initialPrice)
//                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//            }
//            tvAssetFinalPrice.text = PriceFormat.getFormattedPrice(assetResponse.finalPrice)
//            tvAssetPurchasedDate.text = DateTimeFormat.formatCustomDate(assetResponse.date)
//        }
//    }
}