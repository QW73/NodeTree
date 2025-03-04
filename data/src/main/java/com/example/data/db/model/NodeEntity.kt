package com.example.data.db.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "nodes",
    foreignKeys = [ForeignKey(
        entity = NodeEntity::class,
        parentColumns = ["id"],
        childColumns = ["parentId"],
        onDelete = ForeignKey.CASCADE
    )], indices = [Index("parentId")]
)
data class NodeEntity(
    @PrimaryKey val id: String,
    val name: String,
    val parentId: String?
)