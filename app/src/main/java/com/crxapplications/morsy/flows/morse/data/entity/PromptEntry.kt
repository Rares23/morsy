package com.crxapplications.morsy.flows.morse.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "prompts")
data class PromptEntry(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "createdAt") val createdAt: Date
)