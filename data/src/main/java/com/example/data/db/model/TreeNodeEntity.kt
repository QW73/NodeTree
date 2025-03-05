package com.example.data.db.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "nodes", foreignKeys = [ForeignKey(
        entity = TreeNodeEntity::class,
        parentColumns = ["id"],
        childColumns = ["parentId"],
        onDelete = ForeignKey.CASCADE
    )], indices = [Index("parentId"), Index("id")]
)
data class TreeNodeEntity(
    @PrimaryKey val id: String, val name: String, val parentId: String?
)