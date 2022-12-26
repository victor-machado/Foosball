package com.foosball.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.foosball.db.dao.MatchDao
import com.foosball.db.dao.PlayerDao
import com.foosball.db.entities.Match
import com.foosball.db.entities.Player

@Database(entities = [Match::class, Player::class], version = DB_VERSION)
abstract class FoosballDatabase : RoomDatabase() {

    abstract fun matchDao(): MatchDao

    abstract fun playerDao(): PlayerDao

    companion object {
        @Volatile
        private var databseInstance: FoosballDatabase? = null

        fun getDatabasenIstance(mContext: Context): FoosballDatabase =
            databseInstance ?: synchronized(this) {
                databseInstance ?: buildDatabaseInstance(mContext).also {
                    databseInstance = it
                }
            }

        private fun buildDatabaseInstance(mContext: Context) =
            Room.databaseBuilder(mContext, FoosballDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()

    }
}

const val DB_VERSION = 1
const val DB_NAME = "FoosballDatabase.db"