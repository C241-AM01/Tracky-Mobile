package com.megalogic.tracky.ui.addasset

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.megalogic.tracky.R
import com.megalogic.tracky.databinding.ActivityAddAssetBinding
import android.app.DatePickerDialog
import java.util.*
import android.widget.Button
import android.widget.TextView


class AddAssetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAssetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAssetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDepreciationSpinner()
        pickDate()
        setActions()
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
                    // on below line we are passing context.
                    this@AddAssetActivity,
                    { _, year, monthOfYear, dayOfMonth ->
                        // on below line we are setting
                        // date to our text view.
                        tvPickPurchaseDate.text =
                            (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    },
                    // on below line we are passing year, month
                    // and day for the selected date in our date picker.
                    year,
                    month,
                    day
                )
                // at last we are calling show
                // to display our date picker dialog.
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
        // code here
    }
}