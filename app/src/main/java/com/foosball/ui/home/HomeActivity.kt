package com.foosball.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.foosball.R
import com.foosball.ui.matches.MatchesActivity
import com.foosball.ui.play.PlayActivity
import com.foosball.ui.ranking.RankingActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btnPlay.setOnClickListener{
            val intent = Intent(this, PlayActivity::class.java)
            startActivity(intent)
        }

        btnMatches.setOnClickListener{
            val intent = Intent(this, MatchesActivity::class.java)
            startActivity(intent)
        }

        btnRankings.setOnClickListener{
            val intent = Intent(this, RankingActivity::class.java)
            startActivity(intent)
        }
    }
}