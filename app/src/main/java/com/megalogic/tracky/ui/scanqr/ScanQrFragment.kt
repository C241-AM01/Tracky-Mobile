package com.megalogic.tracky.ui.scanqr

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri

import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.megalogic.tracky.R

import com.megalogic.tracky.databinding.FragmentScanQrBinding
import com.megalogic.tracky.data.ResultState
import com.megalogic.tracky.data.ViewModelFactory
import com.megalogic.tracky.ui.assetlist.AdminAssetListViewModel
import com.megalogic.tracky.data.model.AssetsItem

import java.io.IOException

class ScanQrFragment : Fragment() {

    private val requestCodeCameraPermission = 1001
    private lateinit var cameraSource: CameraSource
    private lateinit var barcodeDetector: BarcodeDetector
    private var scannedValue = ""
    private var _binding: FragmentScanQrBinding? = null
    private val binding get() = _binding!!

    private lateinit var factory: ViewModelFactory
    private val viewModel: AdminAssetListViewModel by viewModels { factory }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            setupControls()
        } else {
            Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanQrBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireContext())

        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            askForCameraPermission()
        } else {
            setupControls()
        }

        val aniSlide: Animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.scanner_animation)
        binding.barcodeLine.startAnimation(aniSlide)

//        viewModel.assetData.observe(viewLifecycleOwner, Observer { result ->
//            when (result) {
//                is ResultState.Success -> {
//                    displayAssetDetails(result.data)
//                }
//                is ResultState.Error -> {
//                    Toast.makeText(requireContext(), "Error fetching asset details", Toast.LENGTH_SHORT).show()
//                }
//                is ResultState.Loading -> {
//                    // Handle loading state if necessary
//                }
//            }
//        })
    }

    private fun setupControls() {
        barcodeDetector =
            BarcodeDetector.Builder(requireContext()).setBarcodeFormats(Barcode.ALL_FORMATS).build()

        cameraSource = CameraSource.Builder(requireContext(), barcodeDetector)
            .setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true) //you should add this feature
            .build()

        binding.cameraSurfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            @SuppressLint("MissingPermission")
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    //Start preview after 1s delay
                    cameraSource.start(holder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            @SuppressLint("MissingPermission")
            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                try {
                    cameraSource.start(holder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })

        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
                Toast.makeText(requireContext(), "Scanner has been closed", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                if (!isAdded || activity == null) {
                    return
                }
                val barcodes = detections.detectedItems
                if (barcodes.size() > 0) {
                    scannedValue = barcodes.valueAt(0).rawValue
//                    fetchAssetDetails(scannedValue)

                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(scannedValue))
                    startActivity(intent)
                } else {
                    activity?.runOnUiThread {
                        Toast.makeText(requireContext(), "Scan valid QR code", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

//    private fun fetchAssetDetails(qrCode: String) {
//        val assetId = extractAssetIdFromQrCode(qrCode)
//        if (assetId != null) {
//            viewModel.getAssetDetail(assetId)
//        } else {
//            Toast.makeText(requireContext(), "Invalid QR code", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun extractAssetIdFromQrCode(qrCode: String): Int? {
//        // Assuming the QR code contains the asset ID, otherwise modify this function accordingly
//        return qrCode.toIntOrNull()
//    }
//
//    private fun displayAssetDetails(asset: AssetsItem) {
//        activity?.runOnUiThread {
//            binding.itemAssetLayout.visibility = View.VISIBLE
//            binding.itemAssetLayout.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up))
//
//
//            binding.tvName.text = asset.name
//            binding.assetDescription.text = asset.description
//            binding.assetPurchaseDate.text = asset.purchaseDate
//            binding.assetOriginalPrice.text = asset.originalPrice
//            binding.assetCurrentPrice.text = asset.currentPrice.toString()
//            // Set other fields as needed
//        }
//    }

    private fun askForCameraPermission() {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cameraSource.stop()
        _binding = null
    }
}