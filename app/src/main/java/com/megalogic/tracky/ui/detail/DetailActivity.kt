package com.megalogic.tracky.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.megalogic.tracky.R
import com.megalogic.tracky.databinding.ActivityDetailBinding
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.megalogic.tracky.utils.setImageFromUrl
import com.megalogic.tracky.utils.PriceFormat
//import com.megalogic.tracky.utils.DateTimeFormat

class DetailActivity : AppCompatActivity() {

//    private lateinit var viewModel: DetailViewModel
//    private var _binding: ActivityDetailBinding? = null
//    private val binding get() = _binding
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
//        val asset = arguments?.getString("username")
//        viewModel.fetchAssetDetail(assetId!!)
//        observeViewModel()
//    }
//
//    private fun observeViewModel() {
//        viewModel.assetDetail.observe(viewLifecycleOwner) { userDetail ->
//            userDetail?.also { detail ->
//                restartContent(detail)
//            }
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val binding = ActivityDetailBinding.inflate(inflater, container, false)
//        _binding = binding
//        return binding.root
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        clearBinding()
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}