package com.foosball.ui.ranking

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foosball.db.FoosballDatabase
import com.foosball.db.entities.Player
import com.foosball.ui.ranking.model.Rank
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RankingViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private var dataBaseInstance: FoosballDatabase?= null
    var allRankingsList = MutableLiveData<ArrayList<Rank>>()
    var allRankings = ArrayList<Rank>()
    var allPlayers = ArrayList<Player>()
    var allGames = 0
    var desc: Boolean = false
    var oType: Int = 2

    var toast = MutableLiveData("")

    fun setDbInstance(dataBaseInstance: FoosballDatabase) {
        this.dataBaseInstance = dataBaseInstance
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }

    fun getRanking(orderType: Int,descending: Boolean) {
        desc = descending
        oType = orderType
        if(allGames == allRankingsList.value?.size){
            orderList()
        } else {
            dataBaseInstance?.playerDao()?.getAllPlayers()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe ({ allPlayersList ->
                    allPlayers.addAll(allPlayersList)
                    allPlayersList.forEach {player ->
                        getWinCountPlayerId(player)
                    }
                },{
                    toast.postValue(it.message)
                })?.let {
                    compositeDisposable.add(it)
                }
        }
    }

    private fun getWinCountPlayerId(player: Player){
        dataBaseInstance?.matchDao()?.getWinCountByPlayerId(player.playerId!!)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe ({
                allRankings.add(Rank(player.playerId, player.name, null, it))
                if(allRankings.size == allPlayers.size) {
                    allPlayers.forEach { p ->
                        getGamesCountByPlayerId(p.playerId)
                    }
                }
            },{
                toast.postValue(it.message)
            })?.let {
                compositeDisposable.add(it)
            }
    }

    private fun getGamesCountByPlayerId(plrId: Int?) {
        dataBaseInstance?.matchDao()?.getGameCountByPlayerId(plrId!!)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe ({
                val rank = allRankings.first { r -> r.playerId == plrId }
                rank.games = it
                allGames++
                if(allGames == allPlayers.size) {
                    orderList()
                }
            },{
                toast.postValue(it.message)
            })?.let {
                compositeDisposable.add(it)
            }
    }

    private fun orderList() {
        when (oType){
            0 -> {
                if(desc) {
                    allRankings.sortByDescending { it.name }
                } else {
                    allRankings.sortBy { it.name }
                }
            }
            1 -> {
                if(desc) {
                    allRankings.sortByDescending { it.games }
                } else {
                    allRankings.sortBy { it.games }
                }
            }
            2 -> {
                if(desc) {
                    allRankings.sortByDescending { it.wins }
                } else {
                    allRankings.sortBy { it.wins }
                }
            }
        }

        allRankingsList.postValue(allRankings)
    }

}