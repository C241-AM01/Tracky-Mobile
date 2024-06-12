package com.megalogic.tracky.ui.assetlist

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.megalogic.tracky.adapter.AssetListAdapter
import com.megalogic.tracky.data.asset.AssetResponse
import com.megalogic.tracky.data.asset.DummyData
import com.megalogic.tracky.databinding.FragmentAdminAssetListBinding
import com.megalogic.tracky.ui.addasset.AddAssetActivity
import com.megalogic.tracky.ui.detail.AdminDetailAssetFragment
import com.megalogic.tracky.ui.detail.DetailActivity

class AdminAssetListFragment : Fragment() {

    private var _binding: FragmentAdminAssetListBinding? = null

    private val binding get() = _binding!!
    private lateinit var assetAdapter: AssetListAdapter
    private var assetList: List<AssetResponse> = DummyData.itemAsset

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

        // Setup RecyclerView
        assetAdapter = AssetListAdapter(requireContext(), assetList) { asset ->
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
