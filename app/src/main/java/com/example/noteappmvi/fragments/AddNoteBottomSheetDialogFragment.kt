package com.example.noteappmvi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.noteappmvi.databinding.FragmentAddNoteBottomSheetDialogBinding
import com.example.noteappmvi.db.Note
import com.example.noteappmvi.db.NoteDataBase
import com.example.noteappmvi.repository.NoteRealisation
import com.example.noteappmvi.repository.NoteRepository
import com.example.noteappmvi.viewmodel.note.NoteViewIntent
import com.example.noteappmvi.viewmodel.note.NoteViewModel
import com.example.noteappmvi.viewmodel.factories.NoteViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddNoteBottomSheetDialogFragment : BottomSheetDialogFragment() {
    lateinit var bindingClass: FragmentAddNoteBottomSheetDialogBinding
    lateinit var noteDb: NoteDataBase
    lateinit var noteRepository: NoteRepository
    lateinit var noteViewModelFactory: NoteViewModelFactory
    lateinit var noteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingClass = FragmentAddNoteBottomSheetDialogBinding.inflate(layoutInflater)
        return bindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteDb = NoteDataBase.getDb(requireContext())
        noteRepository = NoteRealisation(noteDb)
        noteViewModelFactory = NoteViewModelFactory(noteRepository)
        noteViewModel = ViewModelProvider(this , noteViewModelFactory).get(NoteViewModel::class.java)

        bindingClass.btnAddNote.setOnClickListener {
            addNote()
            dismiss()
        }

    }

    private fun addNote(){
         noteViewModel.noteIntent(NoteViewIntent.AddNote(Note(null, bindingClass.etTitle.text.toString(), bindingClass.etAddNote.text.toString(), 0)))
    }

}