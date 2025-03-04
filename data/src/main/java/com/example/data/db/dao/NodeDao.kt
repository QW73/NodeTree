package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.db.model.NodeEntity

@Dao
interface NodeDao {
    @Query("SELECT * FROM nodes WHERE id = :id")
    suspend fun getNodeById(id: String): NodeEntity?

    @Query("SELECT * FROM nodes WHERE parentId = :parentId")
    suspend fun getChildren(parentId: String): List<NodeEntity>

    @Query("SELECT * FROM nodes WHERE parentId IS NULL LIMIT 1")
    suspend fun getRoot(): NodeEntity?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(node: NodeEntity)

    @Transaction
    @Query("DELETE FROM nodes WHERE id = :id")
    suspend fun delete(id: String)
}

