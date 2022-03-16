package com.example.pennydrop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class RuleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = com.example.pennydrop.databinding.FragmentRuleBinding.inflate(inflater,container,false)
        return binding.root
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_rule, container, false)
    }
}