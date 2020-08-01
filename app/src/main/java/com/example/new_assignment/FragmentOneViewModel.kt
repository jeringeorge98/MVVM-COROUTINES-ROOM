package com.example.new_assignment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FragmentOneViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var db: storyDao
    lateinit var exec : ExecutorService

    init {
        db = StoryDataBase.getDatabase(application).storyDao()
        exec = Executors.newSingleThreadExecutor()
    }

    fun getAllStories(): List<Story> {
        return db.getAll()
    }

    fun insertStory(story: Story){
       db.insertStory(story)
    }

}