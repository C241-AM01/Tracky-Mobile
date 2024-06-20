package com.megalogic.tracky.ui.detailtracker

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.vision.Tracker
import com.megalogic.tracky.R
import com.megalogic.tracky.data.ResultState
import com.megalogic.tracky.data.ViewModelFactory
import com.megalogic.tracky.data.model.AssetsItem
import com.megalogic.tracky.data.model.TrackersItem
import com.megalogic.tracky.databinding.ActivityDetailAssetBinding
import com.megalogic.tracky.databinding.ActivityDetailTrackerBinding
import com.megalogic.tracky.ui.detailasset.DetailAssetViewModel

class DetailTrackerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTrackerBinding

    private lateinit var factory: ViewModelFactory
    private val viewModel: DetailTrackerViewModel by viewModels { factory }

    private var currentTracker: TrackersItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)
        val trackerId = intent.getIntExtra("trackerId", 0)

        viewModel.getTrackerDetail(trackerId)

        viewModel.trackerData.observe(this) { result ->
            when (result) {
                is ResultState.Loading -> {
                    // Show loading state
                }
                is ResultState.Success -> {
                    val tracker = result.data
                    // Update UI with tracker details
                    binding.tvTracker.text = tracker.name
                    binding.tvVehicleName.text = tracker.vehicleType
                    // More UI updates...
                }
                is ResultState.Error -> {
                    // Handle error state
                }
            }
        }
    }
}