package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.db.dao.NodeDao
import com.example.data.db.model.NodeEntity

@Database(entities = [NodeEntity::class], version = 1)
abstract class NodeDatabase : RoomDatabase() {
    abstract fun nodeDao(): NodeDao
}