package com.gallapillo.tiketsnotes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.random.Random

@Entity(
    tableName = Note.TABLE_NAME
)
data class Note(
    val title: String,
    val text: String,
    val createdAt: Long,
    val updatedAt: Long,
    val color: Int = Random.nextInt(0, 15),
    @PrimaryKey val id: Int? = null
) {
    companion object {
        const val TABLE_NAME = "Notes"
    }
}
