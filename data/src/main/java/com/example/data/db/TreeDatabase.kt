package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.db.dao.TreeDao
import com.example.data.db.model.TreeNodeEntity

@Database(entities = [TreeNodeEntity::class], version = 1)
abstract class TreeDatabase : RoomDatabase() {
    abstract fun treeDao(): TreeDao
}