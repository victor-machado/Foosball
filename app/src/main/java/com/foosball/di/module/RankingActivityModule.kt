package com.foosball.di.module

import android.app.Activity
import com.foosball.di.ActivityScope
import com.foosball.ui.ranking.RankingActivity
import dagger.Binds
import dagger.Module

@Module
abstract class RankingActivityModule {

    @ActivityScope
    @Binds
    abstract fun bindView(rankingActivity: RankingActivity): Activity
}
