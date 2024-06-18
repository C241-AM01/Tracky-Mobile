package com.megalogic.tracky.ui.addasset

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.megalogic.tracky.R
import com.megalogic.tracky.databinding.ActivityAddAssetBinding
import java.util.*

class AddAssetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAssetBinding
    private lateinit var ivAssetImage: ImageView

    companion object {
        private const val REQUEST_IMAGE_PICK = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAssetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ivAssetImage = findViewById(R.id.iv_added_image)

        setupDepreciationSpinner()
        pickDate()
        setActions()
        setupImagePicker()
    }

    private fun setActions() {
        binding.apply {
            btnAdd.setOnClickListener {
                handleAddAsset()
            }
        }
    }

    private fun pickDate(){
        binding.apply{
            btnAddAssetDate.setOnClickListener {

                val c = Calendar.getInstance()

                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(
                    this@AddAssetActivity,
                    { _, year, monthOfYear, dayOfMonth ->
                        tvPickPurchaseDate.text =
                            (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    },
                    year,
                    month,
                    day
                )
                datePickerDialog.show()
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
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        finish() // Go back to the previous activity
    }

    private fun setupImagePicker() {
        binding.btnEditImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_IMAGE_PICK)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = data?.data
            ivAssetImage.setImageURI(imageUri)
        }
    }
}