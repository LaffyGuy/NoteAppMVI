package com.example.noteappmvi.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteappmvi.R
import com.example.noteappmvi.adapters.FavoriteNoteAdapter
import com.example.noteappmvi.contract.HasCustomTitle
import com.example.noteappmvi.databinding.FragmentFavoriteBinding
import com.example.noteappmvi.db.NoteDataBase
import com.example.noteappmvi.navigation.navigate
import com.example.noteappmvi.repository.NoteRealisation
import com.example.noteappmvi.repository.NoteRepository
import com.example.noteappmvi.viewmodel.factories.NoteViewModelFactory
import com.example.noteappmvi.viewmodel.favorite.FavoriteNoteViewIntent
import com.example.noteappmvi.viewmodel.favorite.FavoriteNoteViewModel
import kotlinx.coroutines.launch


class FavoriteFragment : Fragment(), HasCustomTitle {
    lateinit var bindingClass: FragmentFavoriteBinding
    lateinit var noteDb: NoteDataBase
    lateinit var noteRepository: NoteRepository
    lateinit var noteViewModelFactory: NoteViewModelFactory
    lateinit var noteViewModel: FavoriteNoteViewModel
    lateinit var favoriteNoteAdapter: FavoriteNoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingClass = FragmentFavoriteBinding.inflate(layoutInflater)
        return bindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteDb = NoteDataBase.getDb(requireContext())
        noteRepository = NoteRealisation(noteDb)
        noteViewModelFactory = NoteViewModelFactory(noteRepository)
        noteViewModel = ViewModelProvider(this, noteViewModelFactory).get(FavoriteNoteViewModel::class.java)

        initFavoriteNoteAdapter()

        getAllFavoriteNotes()

        navigateToDetailsFragment()

        deleteFavoriteNote()

        bindingClass.btnDeleteAllFavoriteNotes.setOnClickListener {
            noteViewModel.noteIntent(FavoriteNoteViewIntent.DeleteAllFavoriteNote)
        }

    }

    private fun initFavoriteNoteAdapter(){
        favoriteNoteAdapter = FavoriteNoteAdapter()
        bindingClass.rcFavoriteNotes.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        bindingClass.rcFavoriteNotes.adapter = favoriteNoteAdapter
    }


    private fun getAllFavoriteNotes(){
        viewLifecycleOwner.lifecycleScope.launch {
            noteViewModel.state.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect{
                if(it.isLoading){
                    bindingClass.progressBar.visibility = View.VISIBLE
                }else{
                    bindingClass.progressBar.visibility = View.GONE
                    favoriteNoteAdapter.differ.submitList(it.data)
                }
            }

        }
    }

    private fun navigateToDetailsFragment(){
        favoriteNoteAdapter.onClick {
            navigate().openDetailsFragment(it)
            navigate().publishResult(it)
        }

    }

    private fun deleteFavoriteNote(){
        favoriteNoteAdapter.deleteFavoriteNote {
            noteViewModel.noteIntent(FavoriteNoteViewIntent.DeleteFavoriteNote(it))
        }
    }

    override fun getTitleRes(): Int {
        return R.string.favorites
    }


}