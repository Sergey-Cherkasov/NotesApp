package br.svcdev.notesapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import br.svcdev.notesapp.R
import br.svcdev.notesapp.repository.model.Note
import kotlinx.android.synthetic.main.note_recyclerview_item.view.*

class NotesRVAdapter(val onItemClick: ((Note) -> Unit)? = null) : RecyclerView.Adapter<NotesRVAdapter.ViewHolder>() {
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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note) {

            with(itemView as CardView) {
                note_title.text = note.title
                note_text.text = note.text
                val color = when(note.color){
                    Note.Color.RED -> R.color.colorRed_500
                    Note.Color.INDIGO -> R.color.colorIndigo_500
                    Note.Color.BLUE -> R.color.colorBlue_500
                    Note.Color.GREEN -> R.color.colorGreen_500
                    Note.Color.YELLOW -> R.color.colorYellow_500
                    Note.Color.ORANGE -> R.color.colorOrange_500
                    Note.Color.WHITE -> R.color.colorWhite
                }
                setCardBackgroundColor(ContextCompat.getColor(itemView.context, color))
                itemView.setOnClickListener{
                    onItemClick?.invoke(note)
                }
            }

        }
    }

}