package com.megalogic.tracky.ui.detailasset

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.megalogic.tracky.databinding.FragmentAdminDetailAssetBinding

class AdminDetailAssetFragment : Fragment() {
    private var _binding: FragmentAdminDetailAssetBinding? = null
    private lateinit var viewModel: AdminDetailAssetViewModel
    private val binding get() = _binding
    companion object {
        fun newInstance() = AdminDetailAssetFragment()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AdminDetailAssetViewModel::class.java]
        val assetId = arguments?.getInt("assetId")
        viewModel.fetchAssetDetail(assetId!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAdminDetailAssetBinding.inflate(inflater, container, false)
        _binding = binding
        return binding.root
    }

    private fun clearBinding() {
        _binding = null
    }
}