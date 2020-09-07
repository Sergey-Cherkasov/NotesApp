package br.svcdev.notesapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.svcdev.notesapp.R
import br.svcdev.notesapp.repository.model.Note
import kotlinx.android.synthetic.main.note_recyclerview_item.view.*

class NotesRVAdapter() : RecyclerView.Adapter<NotesRVAdapter.ViewHolder>() {
    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.note_recyclerview_item, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(notes[position])

    override fun getItemCount(): Int = notes.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note) {
            with(itemView) {
                note_title.text = note.title
                note_text.text = note.text
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    linear_layout.setBackgroundColor(resources.getColor(note.color, context.theme))
                } else {
                    linear_layout.setBackgroundColor(resources.getColor(note.color))
                }
            }

        }
    }

}