package com.example.new_assignment

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.room.*
import java.sql.Date
import java.sql.Timestamp
val date = java.util.Date()

@Entity
data class Story(
    @PrimaryKey(autoGenerate = true) val ID: Int? = null,
    @ColumnInfo (name = "description") val Desc : String?,
    @ColumnInfo(name= "name") val Name: String?,
    @ColumnInfo(name="created_at") val Create: String = java.util.Date().toString(),
    @ColumnInfo (name="uri") val Uri : String?,
    @ColumnInfo (name="emotion") val Emo : String?
)


@Dao
interface storyDao {
    @Query("SELECT * FROM story ORDER BY Id DESC")
    fun getAll(): List<Story>

    @Query("SELECT * FROM story WHERE Id LIKE :ID")
    fun getId(ID: Int): Story

    @Query("SELECT * FROM story ORDER BY Id DESC LIMIT 1")
    fun getLatest(): Story

    @Insert
    fun insertStory(vararg stories: Story)
}

//@Database(entities = arrayOf(Story::class), version = 1)
//abstract class StoryDB : RoomDatabase() {
//    abstract fun storyDao(): storyDao
//}