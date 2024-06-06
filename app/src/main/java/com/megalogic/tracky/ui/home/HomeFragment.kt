package com.megalogic.tracky.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.megalogic.tracky.adapter.AssetListAdapter
import com.megalogic.tracky.data.asset.AssetResponse
import com.megalogic.tracky.data.asset.DummyData
import com.megalogic.tracky.databinding.FragmentHomeBinding
import com.megalogic.tracky.ui.detail.DetailActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var assetAdapter: AssetListAdapter
    private var assetList: List<AssetResponse> = DummyData.itemAsset

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
