package com.foosball.di

import com.foosball.ui.play.match.MatchFragment
import com.foosball.ui.play.newplayer.AddNewPlayerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeAddNewPlayerFragment(): AddNewPlayerFragment
    @ContributesAndroidInjector
    abstract fun contributeMatchFragment(): MatchFragment
}