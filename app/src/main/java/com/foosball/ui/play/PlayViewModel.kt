package com.foosball.ui.play

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.EmptyResultSetException
import com.foosball.db.FoosballDatabase
import com.foosball.db.entities.Match
import com.foosball.db.entities.Player
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers.io
import java.util.*
import java.util.concurrent.TimeUnit

class PlayViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private var dataBaseInstance: FoosballDatabase? = null

    var toast = MutableLiveData("Welcome")
    var newPlayerName = MutableLiveData("")
    var player1 = Player()
    var player2 = Player()
    var p1Score = MutableLiveData(0)
    var p2Score = MutableLiveData(0)
    var allPlayersList = MutableLiveData<List<Player>>()
    var scoreBtnEnabled = MutableLiveData(false)
    var selectPlayersEnabled = MutableLiveData(true)
    var startMatchEnabled = MutableLiveData(true)
    var gameTimer = MutableLiveData(0L)

    fun getAllPlayers() {
        dataBaseInstance?.playerDao()?.getAllPlayers()
            ?.subscribeOn(io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                allPlayersList.postValue(it)
            }, {
                toast.postValue(it.message)
            })?.let {
                compositeDisposable.add(it)
            }
    }

    fun addNewPlayer(playerName: String) {
        if (playerName.isEmpty()) {
            toast.postValue("Input player name")
        } else {
            checkPlayerExists(playerName)
        }
    }

    private fun checkPlayerExists(playerName: String) {
        dataBaseInstance?.playerDao()?.getPlayerByName(playerName)
            ?.subscribeOn(io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                if (it != null) {
                    toast.postValue("Player name already exist")
                }
            }, {
                if (it is EmptyResultSetException) {
                    savePlayerIntoDb(Player(null, playerName))
                }
            })?.let {
                compositeDisposable.add(it)
            }
    }

    private fun savePlayerIntoDb(player: Player) {
        dataBaseInstance?.playerDao()?.insertPlayer(player)
            ?.subscribeOn(io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                toast.postValue("Add new player success")
                newPlayerName.postValue("")
                getAllPlayers()
            }, {
            })?.let {
                compositeDisposable.add(it)
            }
    }

    fun player1Selected(position: Int) {
        if (allPlayersList.value?.size!! > 0) {
            allPlayersList.value?.let {
                player1 = it[position]
            }
        }
    }

    fun player2Selected(position: Int) {
        if (allPlayersList.value?.size!! > 0) {
            allPlayersList.value?.let {
                player2 = it[position]
            }
        }
    }

    fun startMatch() {
        val p1Empty = player1.name?.isEmpty() ?: true
        val p2Empty = player2.name?.isEmpty() ?: true
        val emptyPlayer = p2Empty || p1Empty
        if (emptyPlayer) {
            toast.postValue("Select players to start match")
        } else if (player1.name.equals(player2.name)) {
            toast.postValue("Select different players to start")
        } else {
            p1Score.postValue(0)
            p2Score.postValue(0)
            scoreBtnEnabled.postValue(true)
            selectPlayersEnabled.postValue(false)
            startMatchEnabled.postValue(false)

            Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    gameTimer.postValue(it)
                }.takeUntil { it == MATCH_TIME }
                .doOnComplete {
                    toast.postValue("Match ended")
                    scoreBtnEnabled.postValue(false)
                    selectPlayersEnabled.postValue(true)
                    startMatchEnabled.postValue(true)
                    storeResults()
                }
                .subscribe()?.let {
                    compositeDisposable.add(it)
                }
        }
    }

    private fun storeResults() {
        var p1Win = false
        if (p1Score.value!! > p2Score.value!!) {
            p1Win = true
        }

        var p2Win = false
        if (p2Score.value!! > p1Score.value!!) {
            p2Win = true
        }

        var winId: Int? = null
        if (p1Win) {
            winId = player1.playerId
        }

        if (p2Win) {
            winId = player2.playerId
        }

        val matchData = Match(
            null,
            player1.playerId,
            p1Score.value,
            player2.playerId,
            p2Score.value,
            winId,
            Date().time
        )

        dataBaseInstance?.matchDao()?.insertMatch(matchData)
            ?.subscribeOn(io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
            }, {
            })?.let {
                compositeDisposable.add(it)
            }
    }

    fun addP1Score() {
        var sP1 = p1Score.value!!
        sP1++
        p1Score.postValue(sP1)
    }

    fun addP2Score() {
        var sP2 = p2Score.value!!
        sP2++
        p2Score.postValue(sP2)
    }

    fun setDbInstance(dataBaseInstance: FoosballDatabase) {
        this.dataBaseInstance = dataBaseInstance
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }

    companion object {
        const val MATCH_TIME = 5L
    }
}