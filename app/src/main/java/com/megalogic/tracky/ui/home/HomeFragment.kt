package com.megalogic.tracky.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.megalogic.tracky.adapter.AssetListAdapter
import com.megalogic.tracky.data.ResultState
import com.megalogic.tracky.data.ViewModelFactory
import com.megalogic.tracky.data.asset.AssetResponse
import com.megalogic.tracky.data.asset.DummyData
import com.megalogic.tracky.data.model.AssetsItem
import com.megalogic.tracky.databinding.FragmentHomeBinding
import com.megalogic.tracky.ui.assetlist.AdminAssetListViewModel
import com.megalogic.tracky.ui.detail.DetailActivity
import com.megalogic.tracky.ui.login.LoginActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var assetAdapter: AssetListAdapter
    private var assetList: List<AssetResponse> = DummyData.itemAsset

    private lateinit var factory: ViewModelFactory
    private val viewModel: HomeViewModel by viewModels { factory }

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
        factory = ViewModelFactory.getInstance(requireContext())
        viewModel.getAssetList()

        var assetListResponse: List<AssetsItem> = emptyList()

        viewModel.assetList.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    assetListResponse = result.data.assets as List<AssetsItem>
                }

                is ResultState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, "Please Log in", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, LoginActivity::class.java)
                    viewModel.clearData()
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }

                is ResultState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

        // Setup RecyclerView
        assetAdapter = AssetListAdapter(requireContext(), assetListResponse) { asset ->
            // Handle item click
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("asset", asset)
            startActivity(intent)
        }
        binding.rvAsset.layoutManager = LinearLayoutManager(context)
        binding.rvAsset.adapter = assetAdapter

        // Setup SearchView
        binding.svAsset.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // No action on text submit
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                assetAdapter.filter.filter(newText)
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
