package com.foosball.di.module

import android.app.Activity
import com.foosball.di.ActivityScope
import com.foosball.ui.home.HomeActivity
import dagger.Binds
import dagger.Module

@Module
abstract class HomeActivityModule {

    @ActivityScope
    @Binds
    abstract fun bindView(homeActivity: HomeActivity): Activity
}
