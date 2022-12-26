package com.foosball.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Player.TABLE_NAME)
data class Player(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    var playerId:Int ?= null,
    @ColumnInfo(name = NAME)
    var name: String? = null
) {

    companion object {
        const val TABLE_NAME="player"
        const val ID = "id"
        const val NAME ="name"
    }

}