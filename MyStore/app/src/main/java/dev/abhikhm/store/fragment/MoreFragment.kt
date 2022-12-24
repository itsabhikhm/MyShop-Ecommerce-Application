package dev.abhikhm.store.fragment

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dev.abhikhm.store.databinding.FragmentMoreBinding


class MoreFragment : Fragment() {
    private lateinit var binding : FragmentMoreBinding


    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentMoreBinding.inflate(layoutInflater)

    // Add sections here
        //binding.cardView7.setOnClickListener {

      //  }
        return binding.root
}

}