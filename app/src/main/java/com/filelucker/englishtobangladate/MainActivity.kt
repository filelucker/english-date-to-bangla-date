package com.filelucker.englishtobangladate

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.englishdatetobangladate.Bangla
import com.filelucker.englishtobangladate.databinding.ActivityMainBinding
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val s = Bangla()
        val res = s.getBanglaDate(2022, 4, 3)
        val month = res.getMonth()!!.banglaName

        val dateTime = LocalDate.now()

        Toast.makeText(this, dateTime.toString(), Toast.LENGTH_LONG).show()

        binding.tv.setText(
            res.getDay().toString() + " " + res.getMonth()!!.banglaName + " " + res.getYear()!!
                .toString()
        )

    }
}




