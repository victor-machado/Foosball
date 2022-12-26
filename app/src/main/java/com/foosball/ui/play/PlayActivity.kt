package com.foosball.ui.play

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.foosball.R
import com.foosball.ui.play.match.MatchFragment
import com.foosball.ui.play.newplayer.AddNewPlayerFragment
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_play.*
import javax.inject.Inject

class PlayActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        supportFragmentManager.beginTransaction().add(newPlayerFragment.id, AddNewPlayerFragment()).commit()
        supportFragmentManager.beginTransaction().add(matchFragment.id, MatchFragment()).commit()
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}