package com.foosball.di.module

import android.app.Activity
import com.foosball.di.ActivityScope
import com.foosball.ui.play.PlayActivity
import dagger.Binds
import dagger.Module

@Module
abstract class PlayActivityModule {

    @ActivityScope
    @Binds
    abstract fun bindView(playActivity: PlayActivity): Activity
}