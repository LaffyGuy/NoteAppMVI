package com.example.noteappmvi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappmvi.R
import com.example.noteappmvi.databinding.NoteItemBinding
import com.example.noteappmvi.db.Note

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private var deleteNote: ((Note) -> Unit)? = null
    private var detailsNote: ((Note) -> Unit)? = null
    class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
          val binding = NoteItemBinding.bind(itemView)
          fun bind(item: Note){
              binding.tvNoteTitle.text = item.title
          }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = differ.currentList[position]
        holder.bind(currentNote)

        holder.itemView.setOnClickListener {
            detailsNote?.let { callback ->
                callback(currentNote)
            }
        }
        holder.binding.btnDeleteNote.setOnClickListener {
            deleteNote?.let { callback ->
                callback(currentNote)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun deleteNoteClick(callback: (Note) -> Unit){
        deleteNote = callback
    }

    fun detailsNoteClick(callback: (Note) -> Unit){
        detailsNote = callback
    }

    private var differCallback = object : DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

}