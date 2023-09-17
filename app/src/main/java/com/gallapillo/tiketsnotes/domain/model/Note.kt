package com.gallapillo.tiketsnotes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.random.Random

@Entity(
    tableName = Note.TABLE_NAME
)
data class Note(
    var name: String,
    var text: String,
    val createdAt: Long,
    var updatedAt: Long,
    val color: Int = Random.nextInt(0, 15),
    @PrimaryKey val id: Int? = null
) {
    companion object {
        const val TABLE_NAME = "Notes"
    }
}
