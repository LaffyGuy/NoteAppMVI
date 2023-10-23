package com.example.noteappmvi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappmvi.R
import com.example.noteappmvi.databinding.FavoriteNoteItemBinding
import com.example.noteappmvi.db.Note

class FavoriteNoteAdapter: RecyclerView.Adapter<FavoriteNoteAdapter.FavoriteNoteViewHolder>() {
    private var deleteFavorite: ((Note) -> Unit)? = null
    private var click: ((Note) -> Unit)? = null

    class FavoriteNoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
          val binding = FavoriteNoteItemBinding.bind(itemView)
          fun bind(item: Note){
              binding.tvNoteTitle.text = item.title
          }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteNoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_note_item, parent, false)
        return FavoriteNoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteNoteViewHolder, position: Int) {
        val currentFavoriteNote = differ.currentList[position]
        holder.bind(currentFavoriteNote)

        holder.itemView.setOnClickListener {
            click?.let { callback ->
                callback(currentFavoriteNote)
            }
        }

        holder.binding.btnRemoveFromFavorite.setOnClickListener {
            deleteFavorite?.let { callback ->
                callback(currentFavoriteNote)
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun onClick(callback: (Note) -> Unit){
        click = callback
    }

    fun deleteFavoriteNote(callback: (Note) -> Unit){
        deleteFavorite = callback
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