package com.crxapplications.morsy.flows.morse.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.crxapplications.morsy.flows.morse.data.entity.PromptEntry

@Dao
interface PrompDao {
    @Query("SELECT * FROM prompts")
    fun getAll(): List<PromptEntry>
}