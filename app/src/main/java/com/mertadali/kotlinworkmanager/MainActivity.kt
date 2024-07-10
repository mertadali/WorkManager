package com.mertadali.kotlinworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import com.mertadali.kotlinworkmanager.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    // Global define
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val data = Data.Builder().putInt("IntKey1",1).build()
        // burada constraints worker sınıfından türetiliyor ve isteklerimizi şu şartlar varsa şeklinde yapmamıza olanak sağlar.
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(false)
            .build()

        val myWorkRequest : WorkRequest = OneTimeWorkRequestBuilder<RefreshDatabase>()
            .setConstraints(constraints)
            .setInputData(data)
           // .setInitialDelay(5,TimeUnit.MINUTES)
            .addTag("firstWork")
            .build()


        WorkManager.getInstance(this).enqueue(myWorkRequest)






    }
}