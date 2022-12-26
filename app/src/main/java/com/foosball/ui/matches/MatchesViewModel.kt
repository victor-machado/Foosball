package com.foosball.ui.matches

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foosball.db.FoosballDatabase
import com.foosball.db.entities.Match
import com.foosball.db.entities.Player
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MatchesViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private var dataBaseInstance: FoosballDatabase?= null

    var toast = MutableLiveData("")
    var allMatchesList = MutableLiveData<List<Match>>()
    var allPlayersList = MutableLiveData<List<Player>>()
    var p1NewScore = MutableLiveData(0)
    var p2NewScore = MutableLiveData(0)

    fun getAllMatches() {
        getAllPlayers()
        dataBaseInstance?.matchDao()?.getAllMatches()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe ({
                allMatchesList.postValue(it)
            },{
                toast.postValue(it.message)
            })?.let {
                compositeDisposable.add(it)
            }
    }
    
    fun getAllPlayers() {
        dataBaseInstance?.playerDao()?.getAllPlayers()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe ({
                allPlayersList.postValue(it)
            },{
                toast.postValue(it.message)
            })?.let {
                compositeDisposable.add(it)
            }
    }

    fun getPlayerNameById(pId: Int?): String? {
        return allPlayersList.value?.find { p -> p.playerId == pId }?.name
    }

    fun updateMatch(match: Match) {
        var p1Win = false
        match.p1Score = p1NewScore.value
        match.p2Score = p2NewScore.value

        if(match.p1Score!! > match.p2Score!!){
            p1Win = true
        }

        var p2Win = false
        if(match.p2Score!! > match.p1Score!!){
            p2Win = true
        }

        var winId: Int? = null
        if(p1Win){
            winId = match.p1Id
        }

        if(p2Win){
            winId = match.p2Id
        }

        match.wId = winId

        dataBaseInstance?.matchDao()?.updateMatch(match)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe ({
                toast.postValue("Match update success")
                getAllMatches()
            },{
                toast.postValue(it.message)
            })?.let {
                compositeDisposable.add(it)
            }
    }

    fun setDbInstance(dataBaseInstance: FoosballDatabase) {
        this.dataBaseInstance = dataBaseInstance
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }

}