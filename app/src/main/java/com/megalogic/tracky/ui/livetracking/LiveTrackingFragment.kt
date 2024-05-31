package com.megalogic.tracky.ui.livetracking

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.megalogic.tracky.data.location.DummyDataTracking
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
        setupMap()

        return binding.root
    }

    private fun setupBottomSheet() {
        BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight = 0
            state = BottomSheetBehavior.STATE_COLLAPSED
        }
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
        val polylineOptions = PolylineOptions()
            .addAll(DummyDataTracking.locationHistory)
            .color(Color.BLUE)
            .width(5f)

        googleMap.addPolyline(polylineOptions)
        for (location in DummyDataTracking.locationHistory) {
            googleMap.addMarker(
                MarkerOptions()
                    .position(location)
                    .title("Marker in ${location.latitude}, ${location.longitude}")
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
