package com.example.noteappmvi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteappmvi.R
import com.example.noteappmvi.adapters.NoteAdapter
import com.example.noteappmvi.contract.HasCustomTitle
import com.example.noteappmvi.databinding.FragmentNoteBinding
import com.example.noteappmvi.db.NoteDataBase
import com.example.noteappmvi.navigation.navigate
import com.example.noteappmvi.repository.NoteRealisation
import com.example.noteappmvi.repository.NoteRepository
import com.example.noteappmvi.viewmodel.note.NoteViewModel
import com.example.noteappmvi.viewmodel.factories.NoteViewModelFactory
import com.example.noteappmvi.viewmodel.note.NoteViewIntent
import kotlinx.coroutines.launch


class NoteFragment : Fragment(), HasCustomTitle {
    lateinit var bindingClass: FragmentNoteBinding
    lateinit var noteDb: NoteDataBase
    lateinit var noteRepository: NoteRepository
    lateinit var noteViewModelFactory: NoteViewModelFactory
    lateinit var noteViewModel: NoteViewModel
    lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingClass = FragmentNoteBinding.inflate(layoutInflater)
        return bindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteDb = NoteDataBase.getDb(requireContext())
        noteRepository = NoteRealisation(noteDb)
        noteViewModelFactory = NoteViewModelFactory(noteRepository)
        noteViewModel = ViewModelProvider(this, noteViewModelFactory).get(NoteViewModel::class.java)

        initAdapter()

        note()

        bindingClass.btnAddNote.setOnClickListener{
            navigate().openNoteBottomSheetDialogFragment()
        }

        clickOnNote()

        deleteNote()

    }

    private fun initAdapter(){
        noteAdapter = NoteAdapter()
        bindingClass.rcNote.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        bindingClass.rcNote.adapter = noteAdapter
    }

    private fun note(){
        viewLifecycleOwner.lifecycleScope.launch {
            noteViewModel.state.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect{
                if(it.isLoading){
                   bindingClass.progressBar.visibility = View.VISIBLE
                }else{
                    bindingClass.progressBar.visibility = View.GONE
                    noteAdapter.differ.submitList(it.data)
                }

            }
        }

    }

    private fun clickOnNote(){
        noteAdapter.detailsNoteClick {
             navigate().openDetailsFragment(it)
             navigate().publishResult(it)
        }
    }

    private fun deleteNote(){
        noteAdapter.deleteNoteClick {
            noteViewModel.noteIntent(NoteViewIntent.DeleteNote(it))
        }
    }

    override fun getTitleRes(): Int {
        return R.string.notes
    }

}