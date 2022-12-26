package com.foosball.ui.play.newplayer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.foosball.R
import com.foosball.db.FoosballDatabase
import com.foosball.ui.play.PlayViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_add_new_player.*

class AddNewPlayerFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_add_new_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnAddNewPlayer.setOnClickListener {
            viewModel?.addNewPlayer(edtPlayer.text.toString())
        }

        viewModel?.toast?.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
        viewModel?.newPlayerName?.observe(viewLifecycleOwner) {
            edtPlayer.setText(it)
        }
    }

}