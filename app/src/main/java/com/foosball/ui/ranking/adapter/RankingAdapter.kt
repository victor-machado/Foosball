package com.foosball.ui.ranking.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.foosball.R
import com.foosball.db.FoosballDatabase
import com.foosball.ui.ranking.RankingViewModel
import com.foosball.ui.ranking.model.Rank
import kotlinx.android.synthetic.main.adapter_ranking.view.*

class RankingAdapter(private val activity: FragmentActivity, private val rankList: List<Rank>) : RecyclerView.Adapter<RankingAdapter.ViewHolder>() {

    private var viewModel: RankingViewModel? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_ranking, parent, false)

        viewModel = ViewModelProviders.of(activity)[RankingViewModel::class.java]
        viewModel?.setDbInstance(FoosballDatabase.getDatabasenIstance(activity))

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        if(position == matchesList.size -1) {
//            holder.itemView.vBottom.visibility = View.VISIBLE
//        }
        viewModel?.let { holder.bind(rankList[position], it) }
    }

    override fun getItemCount(): Int {
        return rankList.size
    }

    class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        fun bind(rank: Rank, rankingViewModel: RankingViewModel) {

            itemView.txtPlayerName.text = rank.name
            itemView.txtGames.text = rank.games.toString()
            itemView.txtWins.text = rank.wins.toString()


//            itemView.txtPlayer1Name.text = matchesViewModel.getPlayerNameById(match.p1Id)
//            itemView.txtPlayer2Name.text = matchesViewModel.getPlayerNameById(match.p2Id)
//            itemView.edtPlayer1Score.setText(match.p1Score.toString())
//            itemView.edtPlayer2Score.setText(match.p2Score.toString())
//            val format = SimpleDateFormat("dd/MM/yy HH:mm")
//            val date = match.mDate?.let { Date(it) }?: ""
//            itemView.txtMatchDate.text = format.format(date)
//            itemView.btnEditMatch.setOnClickListener {
//                matchesViewModel.toast.postValue(adapterPosition.toString())
//            }
//
//            itemView.edtPlayer1Score.addTextChangedListener( object : TextWatcher {
//                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                }
//
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    var p1Scr = 0
//                    if(s?.length!! > 0){
//                        p1Scr = Integer.parseInt(s.toString())
//                    }
//                    matchesViewModel.p1NewScore.postValue(p1Scr)
//                }
//
//                override fun afterTextChanged(s: Editable?) {
//                }
//            })
//
//            itemView.edtPlayer2Score.addTextChangedListener( object : TextWatcher {
//                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                }
//
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    var p2Scr = 0
//                    if(s?.length!! > 0){
//                        p2Scr = Integer.parseInt(s.toString())
//                    }
//                    matchesViewModel.p2NewScore.postValue(p2Scr)
//                }
//
//                override fun afterTextChanged(s: Editable?) {
//                }
//            })
//
//            itemView.edtPlayer1Score.onFocusChangeListener =
//                OnFocusChangeListener { _, hasFocus ->
//                    if (!hasFocus){
//                        if(!itemView.edtPlayer2Score.hasFocus()) {
//                            disableEdit(match)
//                        }
//                    }
//                }
//
//            itemView.edtPlayer2Score.onFocusChangeListener =
//                OnFocusChangeListener { _, hasFocus ->
//                    if (!hasFocus){
//                        if(!itemView.edtPlayer1Score.hasFocus()) {
//                            disableEdit(match)
//                        }
//                    }
//                }
//
//            if(match.wId == match.p1Id){
//                itemView.imgP1Winner.visibility = View.VISIBLE
//            }
//
//            if(match.wId == match.p2Id){
//                itemView.imgP2Winner.visibility = View.VISIBLE
//            }
//
//            itemView.btnEditMatch.setOnClickListener {
//                itemView.btnEditMatch.visibility = View.GONE
//                itemView.btnSaveEdit.visibility = View.VISIBLE
//                itemView.btnCancelEdit.visibility = View.VISIBLE
//                itemView.edtPlayer1Score.isEnabled = true
//                itemView.edtPlayer2Score.isEnabled = true
//                itemView.edtPlayer1Score.requestFocus()
//                matchesViewModel.p1NewScore.postValue(match.p1Score)
//                matchesViewModel.p2NewScore.postValue(match.p2Score)
//                itemView
//            }
//
//            itemView.btnCancelEdit.setOnClickListener {
//                disableEdit(match)
//            }
//
//            itemView.btnSaveEdit.setOnClickListener {
//                itemView.btnEditMatch.visibility = View.VISIBLE
//                itemView.btnSaveEdit.visibility = View.GONE
//                itemView.btnCancelEdit.visibility = View.GONE
//                itemView.edtPlayer1Score.isEnabled = false
//                itemView.edtPlayer2Score.isEnabled = false
//                matchesViewModel.updateMatch(match)
//            }
//
//            if(adapterPosition == 0) {
//                itemView.divider.visibility = View.GONE
//            }
//        }

//        private fun disableEdit(match: Match) {
//            itemView.btnEditMatch.visibility = View.VISIBLE
//            itemView.btnSaveEdit.visibility = View.GONE
//            itemView.btnCancelEdit.visibility = View.GONE
//            itemView.edtPlayer1Score.setText(match.p1Score.toString())
//            itemView.edtPlayer1Score.setText(match.p2Score.toString())
//            itemView.edtPlayer1Score.isEnabled = false
//            itemView.edtPlayer2Score.isEnabled = false
        }
    }
}