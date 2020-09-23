package br.svcdev.notesapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import br.svcdev.notesapp.R
import br.svcdev.notesapp.common.extensions.getColorInt
import br.svcdev.notesapp.repository.model.Note
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.note_recyclerview_item.view.*

class NotesRVAdapter(val onItemClick: ((Note) -> Unit)? = null) :
    RecyclerView.Adapter<NotesRVAdapter.ViewHolder>() {
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

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(note: Note) {
            with(itemView as CardView) {
                note_title.text = note.mTitle
                note_text.text = note.mText
                setCardBackgroundColor(note.mColor.getColorInt(context))
                itemView.setOnClickListener{
                    onItemClick?.invoke(note)
                }
            }

        }
    }

}