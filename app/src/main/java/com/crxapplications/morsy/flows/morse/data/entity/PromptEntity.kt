package com.crxapplications.morsy.flows.morse.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.crxapplications.morsy.flows.morse.domain.model.Prompt
import java.util.Date

@Entity(tableName = "prompts")
data class PromptEntity(
    @PrimaryKey(autoGenerate = true) val uid: Long? = null,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "createdAt") val createdAt: Date,
)

fun PromptEntity.toPrompt(): Prompt = Prompt(
    id = uid ?: -1,
    text = text,
    date = createdAt
)