package com.foosball.di

import com.foosball.di.module.HomeActivityModule
import com.foosball.di.module.PlayActivityModule
import com.foosball.di.module.RankingActivityModule
import com.foosball.ui.home.HomeActivity
import com.foosball.ui.matches.MatchesActivity
import com.foosball.ui.play.PlayActivity
import com.foosball.ui.ranking.RankingActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [(HomeActivityModule::class)])
    abstract fun contributeHomeActivityAndroidInjector(): HomeActivity
    @ContributesAndroidInjector(modules = [(PlayActivityModule::class)])
    abstract fun contributePlayActivityAndroidInjector(): PlayActivity
    @ContributesAndroidInjector(modules = [(PlayActivityModule::class)])
    abstract fun contributeMatchesActivityAndroidInjector(): MatchesActivity
    @ContributesAndroidInjector(modules = [(RankingActivityModule::class)])
    abstract fun contributeRankingActivityAndroidInjector(): RankingActivity
}