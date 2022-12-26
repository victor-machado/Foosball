package com.foosball.ui.ranking.model

import androidx.room.ColumnInfo

data class Rank (
    @ColumnInfo(name = "id") var playerId:Int ?= null,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "games") var games:Int ?= null,
    @ColumnInfo(name = "wins") var wins:Int ?= null
)