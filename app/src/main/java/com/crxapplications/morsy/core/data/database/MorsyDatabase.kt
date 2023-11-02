package com.crxapplications.morsy.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.crxapplications.morsy.core.data.converter.Converters
import com.crxapplications.morsy.flows.morse.data.dao.PromptsDao
import com.crxapplications.morsy.flows.morse.data.entity.PromptEntity

@Database(entities = [PromptEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class MorsyDatabase : RoomDatabase() {
    abstract fun promptDao(): PromptsDao
}