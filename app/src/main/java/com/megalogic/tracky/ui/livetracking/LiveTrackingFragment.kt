package com.megalogic.tracky.ui.livetracking

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.megalogic.tracky.databinding.FragmentLiveTrackingBinding
import com.megalogic.tracky.databinding.FragmentNotificationsBinding
import com.megalogic.tracky.ui.notifications.NotificationsViewModel


class LiveTrackingFragment : Fragment()  {
    private var _binding: FragmentLiveTrackingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val livetrackingViewModel =
            ViewModelProvider(this).get(LiveTrackingViewModel::class.java)

        // Initialize view
        _binding = FragmentLiveTrackingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize map fragment
        val supportMapFragment = childFragmentManager.findFragmentById(com.megalogic.tracky.R.id.mapView) as SupportMapFragment

        // Async map
        supportMapFragment.getMapAsync { googleMap ->
            // When map is loaded
            googleMap.setOnMapClickListener { latLng ->
                // When clicked on map
                // Initialize marker options
                val markerOptions = MarkerOptions()
                    .position(latLng)
                    .title("$latLng.latitude : $latLng.longitude") // String template

                // Remove all markers
                googleMap.clear()

                // Animating to zoom the marker
//                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))

                // Add marker on map
                googleMap.addMarker(markerOptions)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}