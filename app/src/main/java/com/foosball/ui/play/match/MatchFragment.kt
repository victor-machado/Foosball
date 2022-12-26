package com.foosball.ui.play.match

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.foosball.R
import com.foosball.db.FoosballDatabase
import com.foosball.ui.play.PlayViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_match.*

class MatchFragment : Fragment() {

    private var viewModel: PlayViewModel? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)

        viewModel = ViewModelProviders.of(requireActivity())[PlayViewModel::class.java]
        viewModel?.setDbInstance(FoosballDatabase.getDatabasenIstance(context))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel?.p1Score?.observe(viewLifecycleOwner) {
            txtScoreP1.text = it.toString()
        }

        viewModel?.p2Score?.observe(viewLifecycleOwner) {
            txtScoreP2.text = it.toString()
        }

        viewModel?.allPlayersList?.observe(viewLifecycleOwner) {
            var pList = it.map { p -> p.name.toString()}
            if(pList.isEmpty()){
                pList = listOf("")
            }
            spnPlayer1.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item_1, pList)
            spnPlayer2.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item_2, pList)
        }

        viewModel?.scoreBtnEnabled?.observe(viewLifecycleOwner){
            btnScoreP1.isEnabled = it
            btnScoreP2.isEnabled = it
        }

        viewModel?.selectPlayersEnabled?.observe(viewLifecycleOwner){
            spnPlayer1.isEnabled = it
            spnPlayer2.isEnabled = it
        }

        viewModel?.startMatchEnabled?.observe(viewLifecycleOwner){
            btnStartMatch.isEnabled = it
        }

        viewModel?.gameTimer?.observe(viewLifecycleOwner){
            txtTimer.text = it.toString()
        }

        viewModel?.getAllPlayers()

        btnScoreP1.setOnClickListener {
            viewModel?.addP1Score()
        }

        btnScoreP2.setOnClickListener {
            viewModel?.addP2Score()
        }

        btnStartMatch.setOnClickListener {
            viewModel?.startMatch()
        }

        spnPlayer1.onItemSelectedListener = object: OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel?.player1Selected(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        spnPlayer2.onItemSelectedListener = object: OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel?.player2Selected(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

    }

}