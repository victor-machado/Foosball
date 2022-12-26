package com.foosball.db.dao

import androidx.room.*
import com.foosball.db.entities.Match
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMatch(match: Match) : Completable

    @Query("SELECT * FROM ${Match.TABLE_NAME}")
    fun getAllMatches(): Single<List<Match>>

    @Query("SELECT COUNT(*) FROM ${Match.TABLE_NAME} WHERE ${Match.WINNER_ID}= :pId")
    fun getWinCountByPlayerId(pId : Int): Single<Int>

    @Query("SELECT * FROM ${Match.TABLE_NAME} WHERE ${Match.WINNER_ID}= :pId")
    fun getMatchesByWinnerId(pId : Int): Single<List<Match>>

    @Query("SELECT COUNT(*) FROM ${Match.TABLE_NAME} WHERE ${Match.P1_ID}= :pId OR ${Match.P2_ID}= :pId")
    fun getGameCountByPlayerId(pId : Int): Single<Int>

    @Query("SELECT * FROM ${Match.TABLE_NAME} WHERE ${Match.P1_ID}= :pId OR ${Match.P2_ID}= :pId")
    fun getGamesByPlayerId(pId : Int): Single<List<Match>>

    @Delete
    fun deleteMatch(match:Match) : Completable

    @Update
    fun updateMatch(match:Match) : Completable
}