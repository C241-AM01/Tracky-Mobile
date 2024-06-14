package com.megalogic.tracky.ui.assetlist

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.megalogic.tracky.adapter.AssetListAdapter
import com.megalogic.tracky.data.ResultState
import com.megalogic.tracky.data.ViewModelFactory
import com.megalogic.tracky.data.asset.AssetResponse
import com.megalogic.tracky.data.asset.DummyData
import com.megalogic.tracky.data.model.AssetListResponse
import com.megalogic.tracky.data.model.AssetsItem
import com.megalogic.tracky.databinding.FragmentAdminAssetListBinding
import com.megalogic.tracky.ui.addasset.AddAssetActivity
import com.megalogic.tracky.ui.detail.AdminDetailAssetFragment
import com.megalogic.tracky.ui.detail.DetailActivity
import com.megalogic.tracky.ui.login.LoginActivity

class AdminAssetListFragment : Fragment() {

    private var _binding: FragmentAdminAssetListBinding? = null

    private val binding get() = _binding!!
    private lateinit var assetAdapter: AssetListAdapter
    private var assetList: List<AssetResponse> = DummyData.itemAsset

    private lateinit var factory: ViewModelFactory
    private val viewModel: AdminAssetListViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminAssetListBinding.inflate(inflater, container, false)
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
                    // Setup RecyclerView
                    assetAdapter = AssetListAdapter(requireContext(), result.data.assets) { asset ->
                        // Handle item click
                        val intent = Intent(context, DetailActivity::class.java)
                        intent.putExtra("assetId", asset.id?.toInt())
                        startActivity(intent)
                    }
                    binding.rvAsset.layoutManager = LinearLayoutManager(context)
                    binding.rvAsset.adapter = assetAdapter
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
                    Toast.makeText(context, "Loading List...", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }



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

        // Setup FAB click listener
        binding.fabAddAsset.setOnClickListener {
            val intent = Intent(requireContext(), AddAssetActivity::class.java)
//             val intent = Intent(context, AddAssetActivity::class.java)

            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}