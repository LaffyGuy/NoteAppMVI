package com.example.noteappmvi.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.noteappmvi.R
import com.example.noteappmvi.databinding.FragmentDetailsBinding
import com.example.noteappmvi.db.Note
import com.example.noteappmvi.db.NoteDataBase
import com.example.noteappmvi.navigation.navigate
import com.example.noteappmvi.repository.NoteRealisation
import com.example.noteappmvi.repository.NoteRepository
import com.example.noteappmvi.viewmodel.favorite.FavoriteNoteViewIntent
import com.example.noteappmvi.viewmodel.factories.NoteViewModelFactory
import com.example.noteappmvi.viewmodel.favorite.FavoriteNoteViewModel


class DetailsFragment : Fragment() {
    lateinit var bindingClass: FragmentDetailsBinding
    lateinit var noteDb: NoteDataBase
    lateinit var noteRepository: NoteRepository
    lateinit var noteViewModelFactory: NoteViewModelFactory
    lateinit var noteViewModel: FavoriteNoteViewModel
    lateinit var note: Note

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingClass = FragmentDetailsBinding.inflate(layoutInflater)
        return bindingClass.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        noteDb = NoteDataBase.getDb(requireContext())
        noteRepository = NoteRealisation(noteDb)
        noteViewModelFactory = NoteViewModelFactory(noteRepository)
        noteViewModel = ViewModelProvider(this, noteViewModelFactory).get(FavoriteNoteViewModel::class.java)

        getInfoAboutNote()

    }

    private fun getInfoAboutNote(){
        navigate().listenResult(Note::class.java, viewLifecycleOwner){ note ->
            if(note.isFavorite == 0){
                bindingClass.tvTitle.text = note.title
                bindingClass.tvNote.text = note.description
                bindingClass.btnAddNoteToFav.setImageResource(R.drawable.ic_favorite_add)
                bindingClass.btnAddNoteToFav.setOnClickListener {
                    noteViewModel.noteIntent(FavoriteNoteViewIntent.AddFavoriteNote(Note(note.id, note.title, note.description, 1)))
                    bindingClass.btnAddNoteToFav.setImageResource(R.drawable.ic_favorite_added)
                }

            }else{
                bindingClass.tvTitle.text = note.title
                bindingClass.tvNote.text = note.description
                bindingClass.btnAddNoteToFav.setImageResource(R.drawable.ic_favorite_added)
                bindingClass.btnAddNoteToFav.setOnClickListener {
                    noteViewModel.noteIntent(FavoriteNoteViewIntent.AddFavoriteNote(Note(note.id, note.title, note.description, 0)))
                    bindingClass.btnAddNoteToFav.setImageResource(R.drawable.ic_favorite_add)
                }

            }

        }
    }



    companion object {

        private val ARG_OPTIONS_NOTE = "ARG_OPTIONS_NOTE"
        private val KEY_OPTIONS = "KEY_OPTIONS"

        fun newInstance(note: Note): DetailsFragment {
            val args = Bundle()
            args.putParcelable(ARG_OPTIONS_NOTE, note)
            val fragment = DetailsFragment()
            fragment.arguments = args
            return fragment
        }

    }

}