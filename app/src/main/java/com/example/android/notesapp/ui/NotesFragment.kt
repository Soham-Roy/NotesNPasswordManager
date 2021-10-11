package com.example.android.notesapp.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.android.notesapp.AppViewModel
import com.example.android.notesapp.R
import com.example.android.notesapp.databinding.FragmentNotesBinding
import com.example.android.notesapp.ui.adapters.ItemAdapter
import com.example.android.notesapp.ui.adapters.ItemClickListener

class NotesFragment : Fragment(), ItemClickListener {

    private val viewModel: AppViewModel by activityViewModels()
    private var _binding: FragmentNotesBinding? = null

    private lateinit var adapter: ItemAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.notesFab.setOnClickListener {
            val action = NotesFragmentDirections.actionNavigationNotesToAddItemFragment()
            findNavController().navigate(action)
        }

        adapter = ItemAdapter(this)
        binding.notesRecyclerView.adapter = adapter
        binding.notesRecyclerView.hasFixedSize()

        viewModel.allNotes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(position: Int) {
        val action = NotesFragmentDirections.actionNavigationNotesToEditItemFragment(
            adapter.getItemByPosition(position).id
        )
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when ( item.itemId ) {
            R.id.settings_button -> {
                val action = NotesFragmentDirections.actionNavigationNotesToSettingsFragment()
                findNavController().navigate(action)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}