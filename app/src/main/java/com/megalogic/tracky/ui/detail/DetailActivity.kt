package com.megalogic.tracky.ui.detail

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.bumptech.glide.Glide
import com.megalogic.tracky.R
import com.megalogic.tracky.adapter.AssetListAdapter
import com.megalogic.tracky.data.asset.AssetResponse
import com.megalogic.tracky.data.asset.DummyData
import com.megalogic.tracky.databinding.ActivityDetailBinding
import com.megalogic.tracky.databinding.ItemAssetBinding
import com.megalogic.tracky.databinding.PopupQrBinding
import com.megalogic.tracky.ui.livetracking.LiveTrackingFragment
import com.megalogic.tracky.utils.setImageFromUrl
//import com.megalogic.tracky.ui.LiveTrackingFragment


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var popupQrBinding: PopupQrBinding? = null
    private var assetResponse: List<AssetResponse> = DummyData.itemAsset
    private var dialog: Dialog? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityDetailBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.btnOpenQrCode.setOnClickListener {
                val asset = DummyData.itemAsset[0] // Get the first asset from the dummy data for example
                showAssetDetailsPopup(asset)
            }

//            binding.btnLiveTrack.setOnClickListener {
//                navigateToFragment(LiveTrackingFragment())
//            }
        }
//        private fun navigateToFragment(fragment: Fragment) {
//            supportFragmentManager.commit {
//                replace(binding.fragment_live_tracking.id, fragment)
//                addToBackStack(null) // Add to back stack to allow navigation back
//            }
//        }

        private fun showAssetDetailsPopup(assetResponse: AssetResponse) {
            // Inflate the item_asset layout
            val popupQrBinding = PopupQrBinding.inflate(LayoutInflater.from(this))

            // Initialize dialog
            dialog = Dialog(this)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.setContentView(popupQrBinding.root)

            // Bind data to the views
            with(popupQrBinding) {
                tvAssetTitle.text = assetResponse.title
                ivQrImage.setImageFromUrl(this@DetailActivity, assetResponse.image)
            }

            binding.btnToolbarBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            // Show the dialog
            dialog?.show()
        }

        private fun showAssetLiveTrack(assetResponse: AssetResponse) {
            // Inflate the item_asset layout
            val popupQrBinding = PopupQrBinding.inflate(LayoutInflater.from(this))

            // Initialize dialog
            dialog = Dialog(this)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.setContentView(popupQrBinding.root)

            // Bind data to the views
            with(popupQrBinding) {
                tvAssetTitle.text = assetResponse.title
                ivQrImage.setImageFromUrl(this@DetailActivity, assetResponse.image)
            }

            binding.btnToolbarBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            // Show the dialog
            dialog?.show()
        }

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


// Extension function to load image from URL
fun ImageView.setImageFromUrl(context: Context, url: String) {
    Glide.with(context).load(url).into(this)
}