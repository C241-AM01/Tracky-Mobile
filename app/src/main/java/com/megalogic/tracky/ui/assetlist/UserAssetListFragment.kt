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
import com.megalogic.tracky.data.PreferenceManager
import com.megalogic.tracky.data.asset.AssetResponse
import com.megalogic.tracky.data.asset.DummyData
import com.megalogic.tracky.databinding.FragmentUserAssetListBinding
import com.megalogic.tracky.ui.addasset.AddAssetActivity
import com.megalogic.tracky.ui.detail.AdminDetailAssetFragment
import com.megalogic.tracky.ui.detail.DetailActivity
import com.megalogic.tracky.ui.profile.ProfileActivity
import com.megalogic.tracky.utils.JWTDecoder

class UserAssetListFragment : Fragment() {

    private var _binding: FragmentUserAssetListBinding? = null
    private lateinit var preferenceManager: PreferenceManager

    private val binding get() = _binding!!
    private lateinit var assetAdapter: AssetListAdapter
    private var assetList: List<AssetResponse> = DummyData.itemAsset

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserAssetListBinding.inflate(inflater, container, false)
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

        // Set OnClickListener for profile button
        binding.btnProfilePage.setOnClickListener {
            val intent = Intent(context, ProfileActivity::class.java)
            startActivity(intent)
        }

        // Initialize preferenceManager
        preferenceManager = PreferenceManager(requireContext())

        // Display the role name
        displayRoleName()
    }

    private fun displayRoleName() {
        val token = preferenceManager.getToken()
        if (token != null) {
            val decodedToken = JWTDecoder.decoded(token)
            val role = decodedToken["role"] as String
            binding.tvRoleName.text = when (role) {
                "admin" -> "Admin"
                "pic" -> "PIC"
                "user" -> "User"
                else -> "Unknown"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}