package com.example.new_assignment

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = arrayOf(Story::class),version = 1,exportSchema = false)
public abstract class StoryDataBase: RoomDatabase() {
    abstract fun storyDao(): storyDao

    companion object{
        // Singelton provides multiple instances of databases openinng
        @Volatile
        private var INSTANCE: StoryDataBase?= null

        fun getDatabase(context:Context):StoryDataBase{
            val tempinstance = INSTANCE
            if(tempinstance!=null){
                return tempinstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                StoryDataBase::class.java,
                "Story"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }

    }
}