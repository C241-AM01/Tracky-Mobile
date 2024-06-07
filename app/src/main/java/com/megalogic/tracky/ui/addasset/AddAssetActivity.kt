package com.megalogic.tracky.ui.addasset

import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout
import com.megalogic.tracky.R
import com.megalogic.tracky.data.api.ApiClient
import com.megalogic.tracky.data.api.ApiService
import com.megalogic.tracky.data.asset.AssetRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddAssetActivity : AppCompatActivity() {

    private lateinit var ivAssetImage: ImageView
    private lateinit var spinnerPeriod: Spinner
    private lateinit var etPurchasedDate: EditText
    private lateinit var etAssetName: EditText
    private lateinit var etDescription: EditText
    private lateinit var etPrice: EditText
    private lateinit var etDepreciationValue: EditText
    private lateinit var tilAssetName: TextInputLayout
    private lateinit var tilDescription: TextInputLayout
    private lateinit var tilPrice: TextInputLayout
    private lateinit var tilDepreciationValue: TextInputLayout
    private lateinit var tilPurchasedDate: TextInputLayout
    private var selectedImageUri: Uri? = null

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            ivAssetImage.setImageURI(uri)
            selectedImageUri = uri
        }
    }

    private val takePhotoLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
        bitmap?.let {
            ivAssetImage.setImageBitmap(bitmap)
            selectedImageUri = Uri.parse(bitmap.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_asset)

        ivAssetImage = findViewById(R.id.iv_asset_image)
        spinnerPeriod = findViewById(R.id.spinner_depreciation_rate)
        etPurchasedDate = findViewById(R.id.et_purchased_date)
        etAssetName = findViewById(R.id.et_asset_name)
        etDescription = findViewById(R.id.et_description)
        etPrice = findViewById(R.id.et_price)
        etDepreciationValue = findViewById(R.id.et_depreciation_value)

        tilAssetName = findViewById(R.id.til_asset_name)
        tilDescription = findViewById(R.id.til_description)
        tilPrice = findViewById(R.id.til_price)
        tilDepreciationValue = findViewById(R.id.til_depreciation_value)
        tilPurchasedDate = findViewById(R.id.til_purchased_date)

        val addButton: Button = findViewById(R.id.btn_add)

        // Set up window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Populate spinner with items
        val depreciationPeriods = arrayOf("Daily", "Weekly", "Monthly", "Yearly")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, depreciationPeriods)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPeriod.adapter = adapter

        findViewById<ImageView>(R.id.fab_add_image).setOnClickListener {
            showImagePickerDialog()
        }

        etPurchasedDate.setOnClickListener {
            showDatePickerDialog()
        }

        addButton.setOnClickListener {
            if (validateInputs()) {
                addAsset()
            }
        }
    }

    private fun showImagePickerDialog() {
        val options = arrayOf("Take Photo", "Choose from Gallery")
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> takePhotoLauncher.launch(null)
                1 -> pickImageLauncher.launch("image/*")
            }
        }
        builder.show()
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            etPurchasedDate.setText(sdf.format(calendar.time))
        }
        DatePickerDialog(this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        if (etAssetName.text.isNullOrEmpty()) {
            tilAssetName.error = "Please enter the asset name"
            isValid = false
        } else {
            tilAssetName.error = null
        }

        if (etDescription.text.isNullOrEmpty()) {
            tilDescription.error = "Please enter the description"
            isValid = false
        } else {
            tilDescription.error = null
        }

        if (etPrice.text.isNullOrEmpty()) {
            tilPrice.error = "Please enter the price"
            isValid = false
        } else {
            tilPrice.error = null
        }

        if (etDepreciationValue.text.isNullOrEmpty()) {
            tilDepreciationValue.error = "Please enter the depreciation value"
            isValid = false
        } else {
            tilDepreciationValue.error = null
        }

        if (etPurchasedDate.text.isNullOrEmpty()) {
            tilPurchasedDate.error = "Please select the purchase date"
            isValid = false
        } else {
            tilPurchasedDate.error = null
        }

        if (selectedImageUri == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun addAsset() {
        val trackerId = 1
        val name = etAssetName.text.toString()
        val description = etDescription.text.toString()
        val purchaseDate = etPurchasedDate.text.toString()
        val depreciationRate = spinnerPeriod.selectedItem.toString().lowercase(Locale.getDefault())
        val depreciationValue = etDepreciationValue.text.toString()
        val originalPrice = etPrice.text.toString()
        val image = selectedImageUri.toString() // Replace this with actual image URL

        val assetRequest = AssetRequest(
            trackerId,
            name,
            description,
            purchaseDate,
            depreciationRate,
            depreciationValue,
            originalPrice,
            image
        )

        val apiService = ApiClient.getClient().create(ApiService::class.java)
        val call = apiService.addAsset(assetRequest)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AddAssetActivity, "Asset added successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@AddAssetActivity, "Failed to add asset", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@AddAssetActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
