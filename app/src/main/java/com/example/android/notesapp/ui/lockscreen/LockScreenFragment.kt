package com.example.android.notesapp.ui.lockscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.android.notesapp.AppViewModel
import com.example.android.notesapp.R
import com.example.android.notesapp.databinding.FragmentLockScreenBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class LockScreenFragment : Fragment() {

    private lateinit var binding : FragmentLockScreenBinding
    private val viewModel : AppViewModel by activityViewModels()
    private lateinit var bottomNav : BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockScreenBinding.inflate(layoutInflater)
        bottomNav = activity?.findViewById(R.id.nav_view)!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.pinButton.setOnClickListener { check() }
    }

    private fun check() {
        val num = binding.pinInputText.text.toString()
        if (num != viewModel.getPin()) {
            binding.pinInputLayout.error = getString(R.string.pin_error)
            return
        }
        val action = LockScreenFragmentDirections.actionLockScreenFragmentToNavigationNotes()
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        bottomNav.visibility = View.INVISIBLE
        if ( !viewModel.pinEnabled ){
            val action = LockScreenFragmentDirections.actionLockScreenFragmentToNavigationNotes()
            findNavController().navigate(action)
        }
    }

    override fun onStop() {
        super.onStop()
        bottomNav.visibility = View.VISIBLE
    }
}