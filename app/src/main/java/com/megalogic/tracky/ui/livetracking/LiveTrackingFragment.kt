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

        return binding.root
    }

    private fun setupBottomSheet() {
        BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight = 100
            state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun setupRecyclerView() {
        val adapter = TrackingListAdapter(requireContext(), DummyDataTracking.trackingData.map {
            TrackingResponse(it["tracker_id"] as Int, it["lat"] as String, it["lon"] as String, it["timestamp"] as String)
        })
        binding.rvHistoryLocation.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistoryLocation.adapter = adapter
    }

    private fun setupMap() {
        val supportMapFragment = childFragmentManager.findFragmentById(com.megalogic.tracky.R.id.mapView) as SupportMapFragment
        supportMapFragment.getMapAsync { map ->
            googleMap = map
            configureUiSettings(googleMap)
            drawPolylineAndMarkersFromDummyData()
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

    private fun drawPolylineAndMarkersFromDummyData() {
        val locationHistory = DummyDataTracking.locationHistory
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
                    .title("Origin: ${origin.latitude}, ${origin.longitude}")
            )

            googleMap.addMarker(
                MarkerOptions()
                    .position(destination)
                    .title("Destination: ${destination.latitude}, ${destination.longitude}")
            )

            // Move camera to show the whole route
            val boundsBuilder = LatLngBounds.Builder()
            boundsBuilder.include(origin)
            boundsBuilder.include(destination)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 100))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
