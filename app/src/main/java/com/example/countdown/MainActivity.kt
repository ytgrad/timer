package com.example.countdown

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.countdown.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var countdownTimer: CountDownTimer? = null
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
        binding.btnStart.setOnClickListener {
            val hours = if (binding.etHours.text.toString().toLongOrNull() == null) 0 else binding.etHours.text.toString().toLong()
            val minutes = if (binding.etMinutes.text.toString().toLongOrNull() == null) 0 else binding.etMinutes.text.toString().toLong()
            val seconds = if (binding.etSeconds.text.toString().toLongOrNull() == null) 0 else binding.etSeconds.text.toString().toLong()
            val ms = if (binding.etMilliseconds.text.toString().toLongOrNull() == null) 0 else binding.etMilliseconds.text.toString().toLong()
            val duration = hours * 3600000 + minutes * 60000 + seconds * 1000 + ms
            startCountdown(duration)
        }
    }

    private fun startCountdown(duration: Long){
            countdownTimer?.cancel()
            countdownTimer = object : CountDownTimer(duration, 10){
                override fun onTick(millisUntilFinished: Long) {
                    val hrs = ( millisUntilFinished / 3600000 ).toInt()
                    val mins = ( millisUntilFinished / 60000 % 60 ).toInt()
                    val secs = ( millisUntilFinished / 1000 % 60 ).toInt()
                    val ms = ( millisUntilFinished % 1000 / 10 ).toInt()

                    binding.etHours.setText(String.format("%02d", hrs))
                    binding.etMinutes.setText(String.format("%02d", mins))
                    binding.etSeconds.setText(String.format("%02d", secs))
                    binding.etMilliseconds.setText(String.format("%02d", ms))
                }

                override fun onFinish() {
                    binding.etHours.setText(R.string._00)
                    binding.etMinutes.setText(R.string._00)
                    binding.etSeconds.setText(R.string._00)
                    binding.etMilliseconds.setText(R.string._00)
                }
            }.start()
    }

}