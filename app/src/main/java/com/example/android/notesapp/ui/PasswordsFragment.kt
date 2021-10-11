package com.example.android.notesapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.android.notesapp.AppViewModel
import com.example.android.notesapp.databinding.FragmentPasswordsBinding
import com.example.android.notesapp.ui.adapters.ItemAdapter
import com.example.android.notesapp.ui.adapters.ItemClickListener

class PasswordsFragment : Fragment(), ItemClickListener {

    private val viewModel: AppViewModel by activityViewModels()
    private var _binding: FragmentPasswordsBinding? = null

    private lateinit var adapter: ItemAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPasswordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.passwordsFab.setOnClickListener {
            val action = PasswordsFragmentDirections.actionNavigationPasswordsToAddItemFragment()
            findNavController().navigate(action)
        }

        adapter = ItemAdapter(this)
        binding.passwordsRecyclerView.adapter = adapter
        binding.passwordsRecyclerView.hasFixedSize()

        viewModel.allPasswords.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onItemClicked(position: Int) {
        val action = PasswordsFragmentDirections.actionNavigationPasswordsToEditItemFragment(
            adapter.getItemByPosition(position).id
        )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}