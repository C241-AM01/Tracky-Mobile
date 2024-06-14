package com.megalogic.tracky.ui.detail

import android.app.Dialog
import android.content.Context
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
import com.megalogic.tracky.R
import com.megalogic.tracky.data.ResultState
import com.megalogic.tracky.data.ViewModelFactory
import com.megalogic.tracky.data.model.AssetsItem
import com.megalogic.tracky.databinding.ActivityDetailBinding
import com.megalogic.tracky.databinding.PopupQrBinding
import com.megalogic.tracky.utils.PriceFormat

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var dialog: Dialog? = null

    private lateinit var factory: ViewModelFactory
    private val viewModel: DetailViewModel by viewModels { factory }

    private var currentAsset: AssetsItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
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
                            ivAssetImage.setImageFromUrl(this@DetailActivity, it)
                        }
                        tvDescription.text = result.data.description
                        tvOriginalPrice.apply {
                            text = PriceFormat.getFormattedPrice(result.data.originalPrice!!.toInt())
                            paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                        }
                        tvCurrentPrice.text = PriceFormat.getFormattedPrice(result.data.currentPrice!!.toInt())
                        tvPurchasedDate.text = DateTimeFormat.formatCustomDate(result.data.purchaseDate!!)
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
                showAssetDetailsPopup(it)
            } ?: Toast.makeText(this, "Asset data is not available", Toast.LENGTH_SHORT).show()
        }

        binding.btnToolbarBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun showAssetDetailsPopup(asset: AssetsItem) {
        // Inflate the popup layout
        val popupQrBinding = PopupQrBinding.inflate(LayoutInflater.from(this))

        // Initialize dialog
        dialog = Dialog(this)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setContentView(popupQrBinding.root)

        // Bind data to the views
        with(popupQrBinding) {
            tvName.text = asset.name
            asset.qrCode?.let { ivQrImage.setImageFromUrl(this@DetailActivity, it) }
        }

        // Show the dialog
        dialog?.show()
    }
}

// Extension function to load image from URL
fun ImageView.setImageFromUrl(context: Context, url: String) {
    Glide.with(context).load(url).into(this)
}
