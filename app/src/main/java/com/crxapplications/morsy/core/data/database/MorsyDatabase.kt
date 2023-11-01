package com.crxapplications.morsy.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.crxapplications.morsy.core.data.converter.Converters
import com.crxapplications.morsy.flows.morse.data.dao.PrompDao
import com.crxapplications.morsy.flows.morse.data.entity.PromptEntry

@Database(entities = [PromptEntry::class], version = 1)
@TypeConverters(Converters::class)
abstract class MorsyDatabase : RoomDatabase() {
    abstract fun promptDao(): PrompDao
}