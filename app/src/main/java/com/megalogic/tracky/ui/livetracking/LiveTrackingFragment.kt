package com.megalogic.tracky.ui.livetracking

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.megalogic.tracky.adapter.TrackingListAdapter
import com.megalogic.tracky.data.location.DummyDataTracking
import com.megalogic.tracky.data.location.TrackingResponse
import com.megalogic.tracky.databinding.FragmentLiveTrackingBinding

class LiveTrackingFragment : Fragment() {

    private var _binding: FragmentLiveTrackingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LiveTrackingViewModel
    private lateinit var googleMap: GoogleMap
    private var currentTrackerId = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLiveTrackingBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(LiveTrackingViewModel::class.java)

        setupBottomSheet()
        setupRecyclerView()
        setupMap()
        setupChangeButton()

        return binding.root
    }

    private fun setupBottomSheet() {
        BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight = 100
            state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun setupRecyclerView() {
        updateRecyclerView(currentTrackerId)
    }

    private fun setupMap() {
        val supportMapFragment = childFragmentManager.findFragmentById(com.megalogic.tracky.R.id.mapView) as SupportMapFragment
        supportMapFragment.getMapAsync { map ->
            googleMap = map
            configureUiSettings(googleMap)
            drawPolylineAndMarkersFromDummyData(currentTrackerId)
        }
    }

    private fun configureUiSettings(googleMap: GoogleMap) {
        googleMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isCompassEnabled = true
            isMyLocationButtonEnabled = true
            isMapToolbarEnabled = true
            isRotateGesturesEnabled = true
            isScrollGesturesEnabled = true
            isTiltGesturesEnabled = true
            isZoomGesturesEnabled = true
        }
    }

    private fun drawPolylineAndMarkersFromDummyData(trackerId: Int) {
        val locationHistory = DummyDataTracking.locationHistory.filter {
            DummyDataTracking.trackingData.find { data ->
                data["tracker_id"] == trackerId &&
                        data["lat"] == it.latitude.toString() &&
                        data["lon"] == it.longitude.toString()
            } != null
        }

        googleMap.clear()
        val polylineOptions = PolylineOptions()
            .addAll(locationHistory)
            .color(Color.BLUE)
            .width(5f)

        googleMap.addPolyline(polylineOptions)

        if (locationHistory.isNotEmpty()) {
            val origin = locationHistory.first()
            val destination = locationHistory.last()

            googleMap.addMarker(
                MarkerOptions()
                    .position(origin)
                    .title("Origin: %.5f, %.5f".format(origin.latitude, origin.longitude))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            )

            googleMap.addMarker(
                MarkerOptions()
                    .position(destination)
                    .title("Destination: %.5f, %.5f".format(destination.latitude, destination.longitude))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            )

            // Move camera to show the whole route
            val boundsBuilder = LatLngBounds.Builder()
            boundsBuilder.include(origin)
            boundsBuilder.include(destination)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 400))
        }
    }


    private fun setupChangeButton() {
        val trackerIds = DummyDataTracking.trackingData.map { it["tracker_id"] as Int }.distinct()
        var currentIndex = trackerIds.indexOf(currentTrackerId)

        binding.btnChange.setOnClickListener {
            currentIndex = (currentIndex + 1) % trackerIds.size
            currentTrackerId = trackerIds[currentIndex]
            updateTrackerInfo(currentTrackerId)
            updateRecyclerView(currentTrackerId)
            drawPolylineAndMarkersFromDummyData(currentTrackerId)
        }
    }


    private fun updateTrackerInfo(trackerId: Int) {
        val trackerData = DummyDataTracking.trackingData.find { it["tracker_id"] == trackerId }
        trackerData?.let {
            binding.tvTracker.text = "Tracker $trackerId"
            binding.tvAssetName.text = it["asset_name"] as String
        }
    }

    private fun updateRecyclerView(trackerId: Int) {
        val filteredData = DummyDataTracking.trackingData.filter { it["tracker_id"] == trackerId }
        val adapter = TrackingListAdapter(requireContext(), filteredData.map {
            TrackingResponse(it["tracker_id"] as Int, it["lat"] as String, it["lon"] as String, it["timestamp"] as String, it["asset_name"] as String)
        })
        binding.rvHistoryLocation.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistoryLocation.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
