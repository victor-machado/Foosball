package com.foosball.di

import com.foosball.FoosballApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class,
    ActivityModule::class,
    FragmentModule::class])
interface AppComponent {
    fun inject(foosballApp: FoosballApp)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun foosballAppBind(foosballApp: FoosballApp): Builder

    }
}