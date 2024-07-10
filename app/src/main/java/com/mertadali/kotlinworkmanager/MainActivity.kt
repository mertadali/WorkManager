package com.mertadali.kotlinworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
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

        /* Bir kereye mahsus olan işler için alttaki kısım kullanılacak.

        val myWorkRequest : WorkRequest = OneTimeWorkRequestBuilder<RefreshDatabase>()
            .setConstraints(constraints)
            .setInputData(data)
            .setInitialDelay(5,TimeUnit.MINUTES)
            .addTag("firstWork")
            .build()

        WorkManager.getInstance(this).enqueue(myWorkRequest)
        */

        // Periodic işlemler için kullanılacak yapı.

        val myPerWorkRequest : PeriodicWorkRequest = PeriodicWorkRequestBuilder<RefreshDatabase>(15,TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setInputData(data)
            .addTag("secondWork")
            .build()

        WorkManager.getInstance(this).enqueue(myPerWorkRequest)

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(myPerWorkRequest.id).observe(this,
            Observer {
                if (it.state == WorkInfo.State.RUNNING){
                    println("running")

                }else if (it.state == WorkInfo.State.FAILED){
                    println("failed")

                }else if (it.state == WorkInfo.State.SUCCEEDED){
                    println("succeeded")
                }
            })

        // İşleri iptal etmek için:
        //WorkManager.getInstance(this).cancelAllWork()

        // Chaining - Zincirleme yani bu iş bittikten sonra bunu yap gibi işlemler için kullanılır. Bu işlem sadece OneTimeRequestlerde kullanılır.

        val oneTimeWorkRequest : OneTimeWorkRequest = OneTimeWorkRequestBuilder<RefreshDatabase>()
            .setConstraints(constraints)
            .setInputData(data)
            .addTag("chainingWork")
            .build()

        WorkManager.getInstance(this)
            .beginWith(oneTimeWorkRequest)
            .then(oneTimeWorkRequest)
            .enqueue()







    }
}