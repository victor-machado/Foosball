package com.foosball.ui.matches

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.foosball.R
import com.foosball.db.FoosballDatabase
import com.foosball.ui.matches.adapter.MatchesAdapter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_matches.*

class MatchesActivity : AppCompatActivity() {

    private var viewModel: MatchesViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matches)

        viewModel = ViewModelProviders.of(this)[MatchesViewModel::class.java]
        viewModel?.setDbInstance(FoosballDatabase.getDatabasenIstance(this))

        viewModel?.allMatchesList?.observe(this) {
            rclMatches.apply {
                layoutManager = LinearLayoutManager(this@MatchesActivity)
                adapter = MatchesAdapter(this@MatchesActivity, it)
            }
        }

        viewModel?.toast?.observe(this) {
            if(!it.isNullOrEmpty()){
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }

        viewModel?.getAllMatches()
    }
}