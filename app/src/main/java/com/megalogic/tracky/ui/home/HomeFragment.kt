package com.megalogic.tracky.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.megalogic.tracky.adapter.AssetListAdapter
import com.megalogic.tracky.data.api.ApiConfig
import com.megalogic.tracky.data.PreferenceManager
import com.megalogic.tracky.data.model.AssetResponse
import com.megalogic.tracky.databinding.FragmentHomeBinding
import com.megalogic.tracky.ui.detail.DetailActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var assetAdapter: AssetListAdapter
    private var assetList: List<AssetResponse> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        fetchAssets()

        binding.svAsset.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                assetAdapter.filter.filter(newText)
                return true
            }
        })
    }

    private fun setupRecyclerView() {
        assetAdapter = AssetListAdapter(requireContext(), assetList) { asset ->
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("asset", asset)
            startActivity(intent)
        }
        binding.rvAsset.layoutManager = LinearLayoutManager(context)
        binding.rvAsset.adapter = assetAdapter
    }

    private fun fetchAssets() {
        val preferenceManager = PreferenceManager(requireContext())
        val token = preferenceManager.getToken()
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    ApiConfig.getApiService().getAssets("Bearer $token")
                }
                if (response.isSuccessful) {
                    val assets = response.body()?.assets ?: listOf()
                    assetList = assets
                    updateAssetList()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("HomeFragment", "Error fetching assets: $errorBody")
                }
            } catch (e: Exception) {
                Log.e("HomeFragment", "Exception: ${e.message}")
            }
        }
    }

    private fun updateAssetList() {
        assetAdapter.updateData(assetList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
