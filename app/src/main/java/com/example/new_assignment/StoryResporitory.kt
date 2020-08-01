package com.example.new_assignment

import androidx.lifecycle.LiveData

class StoryRepository(private val storyDao: storyDao) {
    val allStories : List<Story> = storyDao.getAll()

    suspend fun insert(story: Story){
        storyDao.insertStory(story)
    }
//    fun getStories():LiveData<List<Story>>{
//        val items: LiveData<List<Story>>?
//
//        items=storyDao().getAll()
//        return items
//    }
}