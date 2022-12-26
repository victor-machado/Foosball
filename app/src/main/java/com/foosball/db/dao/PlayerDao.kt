package com.foosball.db.dao

import androidx.room.*
import com.foosball.db.entities.Match
import com.foosball.db.entities.Player
import com.foosball.ui.ranking.model.Rank
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayer(player: Player) : Completable

    @Query("SELECT * FROM ${Player.TABLE_NAME}")
    fun getAllPlayers(): Single<List<Player>>

    @Query("SELECT * FROM ${Player.TABLE_NAME} WHERE ${Player.NAME}= :name")
    fun getPlayerByName(name: String): Single<Player>

    @Delete
    fun deletePlayer(player: Player) : Completable

    @Update
    fun updatePlayer(player: Player)
}