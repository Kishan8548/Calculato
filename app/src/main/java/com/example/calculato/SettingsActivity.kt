package com.example.calculato

import android.content.Context
import android.content.SharedPreferences
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.MenuItem
import android.widget.SeekBar
import android.widget.Switch
import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
// SettingsActivity.kt

class SettingsActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("calculator_settings", Context.MODE_PRIVATE)

        // Setup toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupSettings()
    }

    override fun setSupportActionBar(toolbar: androidx.appcompat.widget.Toolbar?) {
        super.setSupportActionBar(toolbar)
        // Your custom code here
    }

    private fun setupSettings() {
        // Dark Mode Switch
        val switchDarkMode = findViewById<Switch>(R.id.switchDarkMode)
        switchDarkMode.isChecked = sharedPreferences.getBoolean("dark_mode", false)
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("dark_mode", isChecked).apply()
            updateTheme(isChecked)
            fun updateTheme(isDarkMode: Boolean) {
                if (isDarkMode) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }

        // Vibration Switch
        val switchVibration = findViewById<Switch>(R.id.switchVibration)
        switchVibration.isChecked = sharedPreferences.getBoolean("vibration", true)
        switchVibration.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("vibration", isChecked).apply()
            if (isChecked) {
                // Test vibration
                val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(50)
                }
            }
        }

        // Sound Switch and Volume
        val switchSound = findViewById<Switch>(R.id.switchSound)
        val seekBarVolume = findViewById<SeekBar>(R.id.seekBarVolume)

        switchSound.isChecked = sharedPreferences.getBoolean("sound", true)
        seekBarVolume.progress = sharedPreferences.getInt("volume", 50)

        switchSound.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("sound", isChecked).apply()
            seekBarVolume.isEnabled = isChecked
            if (isChecked) {
                playTestSound()
            }
        }

        seekBarVolume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                sharedPreferences.edit().putInt("volume", progress).apply()
                if (fromUser) {
                    playTestSound()
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun updateTheme(isDarkMode: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    private fun playTestSound() {
        if (sharedPreferences.getBoolean("sound", true)) {
            val volume = sharedPreferences.getInt("volume", 50) / 100f
            val toneGen = ToneGenerator(AudioManager.STREAM_MUSIC, (volume * 100).toInt())
            toneGen.startTone(ToneGenerator.TONE_PROP_BEEP, 150)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}