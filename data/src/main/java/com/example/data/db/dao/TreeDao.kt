package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.db.model.TreeNodeEntity

@Dao
interface TreeDao {
    @Query("SELECT * FROM nodes WHERE id = :id")
    suspend fun getNodeById(id: String): TreeNodeEntity?

    @Query("SELECT * FROM nodes WHERE parentId = :parentId")
    suspend fun getChildren(parentId: String): List<TreeNodeEntity>

    @Query("SELECT * FROM nodes WHERE parentId IS NULL LIMIT 1")
    suspend fun getRoot(): TreeNodeEntity?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(node: TreeNodeEntity)

    @Transaction
    @Query("DELETE FROM nodes WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM nodes")
    suspend fun deleteAll()
}

