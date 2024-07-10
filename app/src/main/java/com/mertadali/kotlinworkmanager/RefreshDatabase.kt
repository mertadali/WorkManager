package com.mertadali.kotlinworkmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

// Worker Sınıfını tamımlamamız gerekiyor.
class RefreshDatabase(val context: Context, workerParams: WorkerParameters) : Worker(context,
    workerParams
) {
    // Implement members diyince çıkıyor doWork
    override fun doWork(): Result {
        val getData = inputData
        val myNumber = getData.getInt("IntKey1",0)
        refreshDatabase(myNumber)
        return Result.success()
    }

    private fun refreshDatabase(myNumber : Int){
        val sharedPreferences = context.getSharedPreferences("com.mertadali.kotlinworkmanager",Context.MODE_PRIVATE)
        var mySavedNumber = sharedPreferences.getInt("myNumber",0)
        mySavedNumber = mySavedNumber + myNumber
        println(mySavedNumber)
        sharedPreferences.edit().putInt("myNumber",mySavedNumber).apply()

    }
}