package com.foosball.ui.ranking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.foosball.R
import com.foosball.db.FoosballDatabase
import com.foosball.ui.ranking.adapter.MarginDecorator
import com.foosball.ui.ranking.adapter.RankingAdapter
import com.google.android.material.tabs.TabLayout
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_ranking.*

class RankingActivity : AppCompatActivity() {
    private var viewModel: RankingViewModel? = null
    private var orderDesc: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        viewModel = ViewModelProviders.of(this)[RankingViewModel::class.java]
        viewModel?.setDbInstance(FoosballDatabase.getDatabasenIstance(this))

        tabColumnsHeader.addTab(tabColumnsHeader.newTab().setText("Name"))
        tabColumnsHeader.addTab(tabColumnsHeader.newTab().setText("Games"))
        tabColumnsHeader.addTab(tabColumnsHeader.newTab().setText("Wins"))

        tabColumnsHeader.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                orderDesc = true
                tab?.setIcon(R.drawable.arrow_drop_up)
                onTabSelected()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.icon = null
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                tab?.icon = null
                if(orderDesc){
                    orderDesc = false
                    tab?.setIcon(R.drawable.arrow_drop_down)
                } else {
                    orderDesc = true
                    tab?.setIcon(R.drawable.arrow_drop_up)
                }
                onTabSelected()
            }
        })

        tabColumnsHeader.getTabAt(2)?.select()
        rclRanking.addItemDecoration(MarginDecorator(16))

        viewModel?.allRankingsList?.observe(this) {
            rclRanking.apply {
                layoutManager = LinearLayoutManager(this@RankingActivity)
                adapter = RankingAdapter(this@RankingActivity, it)
            }
        }
    }

    private fun onTabSelected() {
        viewModel?.getRanking(tabColumnsHeader.selectedTabPosition, orderDesc)
    }
}