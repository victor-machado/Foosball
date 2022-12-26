package com.foosball.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Match.TABLE_NAME)
data class Match(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    var matchID:Int ?= null,
    @ColumnInfo(name = P1_ID)
    var p1Id: Int? = null,
    @ColumnInfo(name = P1_SCORE)
    var p1Score: Int? = null,
    @ColumnInfo(name = P2_ID)
    var p2Id: Int? = null,
    @ColumnInfo(name = P2_SCORE)
    var p2Score: Int? = null,
    @ColumnInfo(name = WINNER_ID)
    var wId: Int? = null,
    @ColumnInfo(name = MATCH_DATE)
    var mDate: Long? = null
)
{
    companion object {
        const val TABLE_NAME="match"
        const val ID = "id"
        const val P1_ID ="p1_id"
        const val P1_SCORE = "p1_score"
        const val P2_ID ="p2_id"
        const val P2_SCORE = "p2_score"
        const val WINNER_ID = "w_id"
        const val MATCH_DATE = "m_date"
    }

//    @TypeConverter
//    fun toDate(dateLong:Long):Date {
//        return Date(dateLong)
//    }
//
//    @TypeConverter
//    fun fromDate(date: Date):Long{
//        return date.time;
//    }
}