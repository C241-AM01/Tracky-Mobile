package com.megalogic.tracky.ui.livetracking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.megalogic.tracky.databinding.FragmentLiveTrackingBinding

class LiveTrackingFragment : Fragment() {
    private var _binding: FragmentLiveTrackingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val livetrackingViewModel =
            ViewModelProvider(this).get(LiveTrackingViewModel::class.java)

        _binding = FragmentLiveTrackingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight = 400
            state = BottomSheetBehavior.STATE_COLLAPSED
        }

        val supportMapFragment = childFragmentManager.findFragmentById(com.megalogic.tracky.R.id.mapView) as SupportMapFragment
        supportMapFragment.getMapAsync { googleMap ->
            googleMap.setOnMapClickListener { latLng ->
                val markerOptions = MarkerOptions()
                    .position(latLng)
                    .title("${latLng.latitude} : ${latLng.longitude}")
                googleMap.clear()
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
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
