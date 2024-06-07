package com.on1k.caloriemacroidealkgcalculate

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.on1k.caloriemacroidealkgcalculate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.kaloriBTN.setOnClickListener {
            if (binding.kaloriLL.visibility == View.GONE){
                binding.kaloriLL.visibility = View.VISIBLE
                binding.makroLL.visibility = View.GONE
                binding.idealkgLL.visibility = View.GONE
            }else{
                binding.kaloriLL.visibility = View.GONE
            }
        }
        binding.makroBTN.setOnClickListener {
            if (binding.makroLL.visibility == View.GONE){
                binding.makroLL.visibility = View.VISIBLE
                binding.kaloriLL.visibility = View.GONE
                binding.idealkgLL.visibility =View.GONE}
            else{binding.idealkgLL.visibility = View.GONE}
        }
        binding.idealkgBTN.setOnClickListener {
            if (binding.idealkgLL.visibility == View.GONE){
                binding.idealkgLL.visibility = View.VISIBLE
                binding.makroLL.visibility = View.GONE
                binding.kaloriLL.visibility = View.GONE}
            else{binding.idealkgLL.visibility = View.GONE}
        }
        binding.hesaplaIKH.setOnClickListener {
            val height = binding.boyIKH.text.toString().toFloatOrNull() ?: 0f
            val gender = if (binding.radioGroup2.checkedRadioButtonId == R.id.erkekIKH) "male" else "female"

            val idealWeight = idealkgcalculate(height, gender)

            binding.sonucIKH.text = "ideal weight: $idealWeight kg"
        }
        binding.hesaplaKH.setOnClickListener {
            val weight = binding.kiloKH.text.toString().toFloatOrNull() ?: 0f
            val height = binding.boyKH.text.toString().toFloatOrNull() ?: 0f
            val age = binding.yasKH.text.toString().toIntOrNull() ?: 0
            val gender = if (binding.radioGroup.checkedRadioButtonId == R.id.erkekKH) "male" else "female"

            val bmr = calculateBMR(weight, height, age, gender)
            val result = "daily calories: $bmr\n\n"+"Bulk: ${bulkcalculate(bmr)}\n"+"Cut: ${cutcalculate(bmr)}"
            binding.sonucKH.text = result
        }
        binding.hesaplaMH.setOnClickListener {
            val weight = binding.kiloMH.text.toString().toFloatOrNull() ?: 0f
            val macros = makrocalculate(weight)
            binding.sonucMH.text = "daily macros:\nProtein: ${macros.first}g\nfat: ${macros.second}g\ncarb: ${macros.third}g"
        }

    }
    private fun calculateBMR(weight: Float, height: Float, age: Int, gender: String): Int {
        var bmr = 0
        if (gender == "male") {
            bmr = ((88.362f * 1.4f)+(13.397f * weight) + (4.799f * height) - (5.677f * age)).toInt()
        } else if (gender == "female") {
            bmr = ((447.593f * 1.4f) + (9.247f * weight) + (3.098f * height) - (4.330f * age)).toInt()
        }
        return bmr
    }
    private fun bulkcalculate(number: Int): Int {
        return (number * 1.25).toInt()
    }

     private fun cutcalculate(number: Int): Int {
        return (number * 0.75).toInt()
    }
     private fun makrocalculate(weight: Float): Triple<Float, Float, Float> {
        val proteinRatio = 2.2f
        val fatRatio = 1.0f
        val carbRatio = 4.0f

        val protein = proteinRatio * weight
        val fat = fatRatio * weight
        val carbs = carbRatio * weight

        return Triple(protein, fat, carbs)
    }
    private fun idealkgcalculate(height: Float, gender: String): Float {
        val idealWeight: Float

        if (gender.equals("male", ignoreCase = true)) {
            idealWeight = (height - 100) - ((height - 100) * 0.1f)
        } else {
            idealWeight = (height - 100) - ((height - 100) * 0.15f)
        }

        return idealWeight
    }
}
