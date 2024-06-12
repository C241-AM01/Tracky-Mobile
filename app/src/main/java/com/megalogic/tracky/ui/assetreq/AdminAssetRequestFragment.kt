package com.megalogic.tracky.ui.assetreq

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.megalogic.tracky.R

class AdminAssetRequestFragment : Fragment() {

    companion object {
        fun newInstance() = AdminAssetRequestFragment()
    }

    private val viewModel: AdminAssetRequestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_admin_asset_request, container, false)
    }
}