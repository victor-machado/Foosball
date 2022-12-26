package com.foosball.di.module

import android.app.Activity
import com.foosball.di.ActivityScope
import com.foosball.ui.matches.MatchesActivity
import dagger.Binds
import dagger.Module

@Module
abstract class MatchesActivityModule {

    @ActivityScope
    @Binds
    abstract fun bindView(matchesActivity: MatchesActivity): Activity
}