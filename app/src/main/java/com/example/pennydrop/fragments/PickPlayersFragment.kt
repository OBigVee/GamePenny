package com.example.pennydrop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.pennydrop.R

import com.example.pennydrop.databinding.FragmentPickPlayersBinding
import com.example.pennydrop.viewmodels.GameViewModel
import com.example.pennydrop.viewmodels.PickPlayersViewModel

class PickPlayersFragment : Fragment()  {
    // supports JDK 1.8
    private val pickPlayersViewModel by activityViewModels<PickPlayersViewModel>()
    private val gameViewModel by activityViewModels<GameViewModel>() //delegation
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = com.example.pennydrop.databinding.
        FragmentPickPlayersBinding.inflate(inflater, container, false)
            .apply {
            this.vm = pickPlayersViewModel

            this.buttonPlayGame.setOnClickListener {
                gameViewModel.startGame(
                    pickPlayersViewModel.players.value?.filter{
                            newPlayer -> newPlayer.isIncluded.get() }?.map{
                            newPlayer -> newPlayer.toPlayer() }?: emptyList()
                )
                findNavController().navigate(R.id.gameFragment)
            }
        }
        return binding.root
    }
}
