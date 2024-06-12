package com.megalogic.tracky.ui.addasset

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.megalogic.tracky.R
import com.megalogic.tracky.databinding.ActivityAddAssetBinding

class AddAssetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAssetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAssetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivToolbarBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        setupDepreciationSpinner()
        setActions()
    }

    private fun setActions() {
        binding.apply {
            btnAdd.setOnClickListener {
                handleAddAsset()
            }
        }
    }

    private fun setupDepreciationSpinner() {
        val spinner: Spinner = findViewById(R.id.spinner_depreciation_period)
        ArrayAdapter.createFromResource(
            this,
            R.array.depreciation_period_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }


    private fun handleAddAsset() {
        // code here
    }
}