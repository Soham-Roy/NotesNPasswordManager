package com.example.android.notesapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android.notesapp.AppViewModel
import com.example.android.notesapp.R
import com.example.android.notesapp.database.Item
import com.example.android.notesapp.databinding.FragmentEditItemBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

class EditItemFragment : Fragment() {

    private lateinit var binding : FragmentEditItemBinding
    private lateinit var bottomNav : BottomNavigationView
    private val args : EditItemFragmentArgs by navArgs()

    private val viewModel : AppViewModel by activityViewModels()

    private lateinit var currentItem : Item
    private var isNote : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomNav = activity?.findViewById(R.id.nav_view)!!
    }

    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditItemBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getItemById(args.id).observe(viewLifecycleOwner) {
            currentItem = it!!
            isNote = it.isNote
            binding.apply {
                titleInputText.setText(it.title)
                descInputText.setText(it.desc)
                if ( it.isNote ) noteRadio.isChecked = true
                else passwordRadio.isChecked = true
            }
        }
        binding.updateButton.setOnClickListener { actionUpdate() }
        binding.deleteButton.setOnClickListener { showDeleteDialog() }

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            isNote = ( checkedId == R.id.note_radio )
        }
    }

    private fun actionUpdate() {
        val title = binding.titleInputText.text.toString().trim()
        if ( title.isEmpty() ){
            binding.titleInputLayout.error = getString(R.string.error_title)
            return
        }
        showUpdateDialog(title)
    }

    private fun showUpdateDialog(title : String){
        val dialog = MaterialAlertDialogBuilder(requireContext())
        dialog
            .setTitle( getString(R.string.update_dialog_title) )
            .setMessage( getString(R.string.update_dialog_msg) )
            .setPositiveButton( getString(R.string.update) ){ _, _ ->
                updateItem(title)
            }
            .setNegativeButton( getString(R.string.cancel) ) { _, _ ->
                dialog.setCancelable(true)
            }
            .show()
    }

    private fun showDeleteDialog() {
        val dialog = MaterialAlertDialogBuilder(requireContext())
        dialog
            .setTitle( getString(R.string.delete_dialog_title) )
            .setMessage( getString(R.string.delete_dialog_msg) )
            .setPositiveButton( getString(R.string.delete) ){ _, _ ->
                viewModel.deleteItem(currentItem)
                findNavController().popBackStack()
            }
            .setNegativeButton( getString(R.string.cancel) ) { _, _ ->
                dialog.setCancelable(true)
            }
            .show()
    }

    private fun updateItem(title: String) {
        val desc = binding.descInputText.text.toString().trim()
        val item = currentItem.copy(
            title = title,
            desc = desc,
            isNote = isNote,
            date = Date()
        )
        viewModel.updateItem(item)

        findNavController().popBackStack()
    }

    override fun onStart() {
        super.onStart()
        bottomNav.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        bottomNav.visibility = View.VISIBLE
    }

}