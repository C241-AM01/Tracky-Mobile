package com.megalogic.tracky.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.megalogic.tracky.adapter.AssetListAdapter
import com.megalogic.tracky.data.PreferenceManager
import com.megalogic.tracky.data.ResultState
import com.megalogic.tracky.data.ViewModelFactory
import com.megalogic.tracky.data.model.AssetsItem
import com.megalogic.tracky.databinding.FragmentUserHomeBinding
import com.megalogic.tracky.ui.detailasset.DetailAssetActivity
import com.megalogic.tracky.ui.login.LoginActivity
import com.megalogic.tracky.utils.JWTDecoder

class UserHomeFragment : Fragment() {
    private var _binding: FragmentUserHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var assetAdapter: AssetListAdapter

    private lateinit var factory: ViewModelFactory
    private val viewModel: HomeViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        factory = ViewModelFactory.getInstance(requireContext())
        viewModel.getAssetList()

        var assetListResponse: List<AssetsItem> = emptyList()

        val sharedPreferences = PreferenceManager(requireContext())
        val token = sharedPreferences.getToken()

        if (token != null) {
            val decodedToken = JWTDecoder.decoded(token)
            val role = decodedToken["role"] as? String ?: "Unknown"

            val displayRole = when (role.toLowerCase()) {
                "admin" -> "Admin"
                "user" -> "User"
                "pic" -> "PIC"
                else -> "Unknown"
            }

            val roleTextView: TextView = binding.tvRoleName
            roleTextView.text = displayRole
        } else {
            Toast.makeText(context, "Token not found. Please log in.", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

        viewModel.assetList.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    assetListResponse = result.data.assets as List<AssetsItem>
                    // Setup RecyclerView
                    assetAdapter = AssetListAdapter(requireContext(), result.data.assets) { asset ->
                        // Handle item click
                        val intent = Intent(context, DetailAssetActivity::class.java)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}