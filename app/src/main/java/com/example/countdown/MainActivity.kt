package com.example.countdown

import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.countdown.databinding.ActivityMainBinding
import com.example.countdown.databinding.DialogBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var countdownTimer: CountDownTimer? = null
    private var remainingMilliseconds = 0L
    private var isTimerPaused = false
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
            showTimeDialog()
        }
        binding.btnPause.setOnClickListener {

        }

    }

    private fun showTimeDialog(){
        val bindingDialog: DialogBinding = DialogBinding.inflate(layoutInflater)
        val dialogBuilder = AlertDialog.Builder(this).setView(bindingDialog.root)
        val timeDialog = dialogBuilder.create()

        bindingDialog.btnStartDialog.setOnClickListener {
            Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()
            val hours = if (bindingDialog.etHours.text.toString().toLongOrNull() == null) 0 else bindingDialog.etHours.text.toString().toLong()
            val mins = if (bindingDialog.etMinutes.text.toString().toLongOrNull() == null) 0 else bindingDialog.etMinutes.text.toString().toLong()
            val secs = if (bindingDialog.etSeconds.text.toString().toLongOrNull() == null) 0 else bindingDialog.etSeconds.text.toString().toLong()
            val ms = if (bindingDialog.etMilliseconds.text.toString().toLongOrNull() == null) 0 else bindingDialog.etMilliseconds.text.toString().toLong()
            val duration = hours*3600000 + mins*60000 + secs*1000 + ms

            startCountdown(duration)
            timeDialog.dismiss()
        }

        timeDialog.show()
    }
    private fun startCountdown(duration: Long){
            countdownTimer?.cancel()

            countdownTimer = object : CountDownTimer(duration, 10){
                override fun onTick(millisUntilFinished: Long) {
                    remainingMilliseconds = millisUntilFinished
                    val hrs = ( millisUntilFinished / 3600000 ).toInt()
                    val mins = ( millisUntilFinished / 60000 % 60 ).toInt()
                    val secs = ( millisUntilFinished / 1000 % 60 ).toInt()
                    val ms = ( millisUntilFinished % 1000 / 10 ).toInt()

                    binding.tvHours.text = String.format("%02d", hrs)
                    binding.tvMinutes.text = String.format("%02d", mins)
                    binding.tvSeconds.text = String.format("%02d", secs)
                    binding.tvMilliseconds.text = String.format("%02d", ms)
                }

                override fun onFinish() {
                    binding.tvHours.text = getString(R.string._00)
                    binding.tvMinutes.text = getString(R.string._00)
                    binding.tvSeconds.text = getString(R.string._00)
                    binding.tvMilliseconds.text = getString(R.string._00)
                }

            }.start()
    }

}