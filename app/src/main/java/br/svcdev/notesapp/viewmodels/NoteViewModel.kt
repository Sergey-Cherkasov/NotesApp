package br.svcdev.notesapp.viewmodels

import androidx.lifecycle.ViewModel
import br.svcdev.notesapp.repository.data.NotesData
import br.svcdev.notesapp.repository.model.Note

class NoteViewModel: ViewModel() {

    private var pendingNote: Note? = null
    fun save(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        pendingNote?.let {
            NotesData.saveNote(it)
        }
    }

}