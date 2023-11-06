package com.crxapplications.morsy.flows.morse.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.crxapplications.morsy.flows.morse.data.entity.PromptEntity

@Dao
interface PromptsDao {
    @Query("SELECT * FROM prompts")
    fun getAll(): List<PromptEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entry: PromptEntity): Long

    @Query("DELETE FROM prompts WHERE uid = :uid")
    fun delete(uid: Long): Int
}