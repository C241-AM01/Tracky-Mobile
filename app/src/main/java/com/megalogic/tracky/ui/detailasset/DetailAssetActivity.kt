package com.megalogic.tracky.ui.detailasset

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.megalogic.tracky.data.ResultState
import com.megalogic.tracky.data.ViewModelFactory
import com.megalogic.tracky.data.model.AssetsItem
import com.megalogic.tracky.databinding.ActivityDetailAssetBinding
import com.megalogic.tracky.databinding.PopupQrBinding
import com.megalogic.tracky.ui.detailtracker.DetailTrackerActivity
import com.megalogic.tracky.utils.PriceFormat
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class DetailAssetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailAssetBinding
    private var dialog: Dialog? = null

    private lateinit var factory: ViewModelFactory
    private val viewModel: DetailAssetViewModel by viewModels { factory }

    private var currentAsset: AssetsItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAssetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)
        val assetId = intent.getIntExtra("assetId", 0)

        viewModel.getAssetDetail(assetId)

        viewModel.assetData.observe(this) { result ->
            when (result) {
                is ResultState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    currentAsset = result.data  // Store the current asset
                    binding.apply {
                        tvTrackerId.text = result.data.trackerId
                        tvName.text = result.data.name
                        result.data.imageURL?.let {
                            ivAssetImage.setImageFromUrl(this@DetailAssetActivity, it)
                        }
                        tvDescription.text = result.data.description
                        tvOriginalPrice.apply {
                            text = PriceFormat.getFormattedPrice(result.data.originalPrice!!.toInt())
                            paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                        }
                        tvCurrentPrice.text = PriceFormat.getFormattedPrice(result.data.currentPrice!!.toInt())
                        tvPurchasedDate.text = DateTimeFormat.formatCustomDate(result.data.purchaseDate!!)
                        tvDepreciationRate.text = when (result.data.depreciationRate) {
                            "daily" -> "Days"
                            "weekly" -> "Weeks"
                            "monthly" -> "Months"
                            "yearly" -> "Years"
                            else -> ""
                        }

                        tvDepreciationPeriod.text = when (result.data.depreciationRate) {
                            "daily" -> "Days"
                            "weekly" -> "Weeks"
                            "monthly" -> "Months"
                            "yearly" -> "Years"
                            else -> ""
                        }

                        tvDepreciationValue.text = result.data.depreciationValue?.let {
                            PriceFormat.getFormattedPrice(
                                it.toInt())
                        }

                        val purchaseDate = LocalDate.parse(result.data.purchaseDate)
                        val today = LocalDate.now()
                        val elapsedTime = ChronoUnit.DAYS.between(purchaseDate, today)
                        val depreciationRate = when (result.data.depreciationRate) {
                            "daily" -> elapsedTime
                            "weekly" -> elapsedTime / 7
                            "monthly" -> elapsedTime / 30
                            "yearly" -> elapsedTime / 365
                            else -> 0
                        }
                        tvDepreciationTime.text = depreciationRate.toString()

                        val depreciationValue = result.data.depreciationValue?.toInt()
                        val originalPrice = result.data.originalPrice?.toInt()
                        val currentPrice = originalPrice?.minus((depreciationValue?.times(depreciationRate)!!))


                        if (currentPrice != null) {
                            tvCurrentPrice.text = PriceFormat.getFormattedPrice(currentPrice.toInt())
                        }
                    }
                }
                is ResultState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Failed to load asset details", Toast.LENGTH_SHORT).show()
                }
                is ResultState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

        binding.btnOpenQrCode.setOnClickListener {
            currentAsset?.let {
                showAssetQRPopup(it)
            } ?: Toast.makeText(this, "Asset data is not available", Toast.LENGTH_SHORT).show()
        }

        binding.btnToolbarBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnLiveTrack.setOnClickListener {
            val trackerId = currentAsset?.trackerId
            if (trackerId != null) {
                val intent = Intent(this, DetailTrackerActivity::class.java).apply {
                    putExtra("trackerId", trackerId)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Tracker ID is not available", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showAssetQRPopup(asset: AssetsItem) {
        val popupQrBinding = PopupQrBinding.inflate(LayoutInflater.from(this))

        dialog = Dialog(this)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setContentView(popupQrBinding.root)

        with(popupQrBinding) {
            tvName.text = asset.name
            asset.qrCode?.let { ivQrImage.setImageFromUrl(this@DetailAssetActivity, it) }
            btnToolbarBack.setOnClickListener{
                dialog?.dismiss()
            }
        }

        dialog?.show()
    }
}

fun ImageView.setImageFromUrl(context: Context, url: String) {
    Glide.with(context).load(url).into(this)
}
