package com.example.android.notesapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android.notesapp.AppViewModel
import com.example.android.notesapp.R
import com.example.android.notesapp.databinding.FragmentAddItemBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AddItemFragment : Fragment() {

    private lateinit var binding : FragmentAddItemBinding
    private lateinit var bottomNav : BottomNavigationView

    private val viewModel : AppViewModel by activityViewModels()

    private var isNote = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomNav = activity?.findViewById(R.id.nav_view)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddItemBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.saveButton.setOnClickListener { actionSave() }
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            isNote = (checkedId == R.id.note_radio)
        }
    }

    private fun actionSave() {
        val title = binding.titleInputText.text.toString().trim()
        if ( title.isEmpty() ){
            binding.titleInputLayout.error = getString(R.string.error_title)
            return
        }
        showSaveDialog(title)
    }

    private fun showSaveDialog(title : String){
        val dialog = MaterialAlertDialogBuilder(requireContext())
        dialog
            .setTitle( getString(R.string.save_dialog_title) )
            .setMessage( getString(R.string.save_dialog_msg) )
            .setPositiveButton( getString(R.string.save) ){ _, _ ->
                saveItem(title)
            }
            .setNegativeButton( getString(R.string.cancel) ) { _, _ ->
                dialog.setCancelable(true)
            }
            .show()
    }

    private fun saveItem(title: String) {
        val desc = binding.descInputText.text.toString().trim()
        viewModel.saveItem(title, desc, isNote)

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