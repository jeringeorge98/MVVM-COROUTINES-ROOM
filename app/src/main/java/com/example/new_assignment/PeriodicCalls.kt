package com.example.new_assignment

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.room.Room
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
//import okhttp3.OkHttpClient

class PeriodicCalls(appContext:Context,params:WorkerParameters)  : CoroutineWorker(appContext,params){

    lateinit var db :StoryDataBase

    init {
        db = StoryDataBase.getDatabase(appContext)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun doWork(): Result {
        try{
//            var ConnMgr = Connectivity().isConnecting(applicationContext)
//            Log.i("periodic", ConnMgr.toString())
            Log.i("checking periodicity","checking")

//            Database call to add values
            adddummy()
        }
        catch(e:Exception){
            return Result.failure()
        }
        return Result.success()

    }

    companion object {
        const val WORK_NAME = "com.example.new_assignment.PeriodicCalls"
    }

    fun adddummy(){
        val apiService=PostApiService()

        var latestStory = db.storyDao().getLatest()
        val userinfo=UserInfo(userId=latestStory.ID,title = latestStory.Name,desc = latestStory.Desc,publishdate = latestStory.Create)

        apiService.addUser(userinfo){
            if(it?.userId!=null){

                Toast.makeText(applicationContext,"Sucess", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(applicationContext,"Failure", Toast.LENGTH_LONG).show()
            }

        }
    }
}