package br.svcdev.notesapp.viewmodels

import br.svcdev.notesapp.repository.data.NotesData
import br.svcdev.notesapp.repository.model.Note
import br.svcdev.notesapp.view.ui.note.NoteData
import kotlinx.coroutines.launch

class NoteViewModel(val notesData: NotesData) : BaseViewModel<NoteData>() {

    private var pendingNote: Note? = null

    fun save(note: Note) {
        pendingNote = note
    }

    fun loadNote(noteId: String) = launch {
        try {
            notesData.getNoteById(noteId)?.let {
                setData(NoteData(note = it))
            }
        } catch (e: Throwable) {
            setError(e)
        }
    }

    override fun onCleared() {
        launch {
            pendingNote?.let {
                notesData.saveNote(it)
            }
        }
    }

    fun deleteNote() = launch {
        try {
            pendingNote?.let { notesData.deleteNote(it.mId) }
            setData(NoteData(isDeleted = true))
        } catch (e: Throwable) {
            setError(e)
        }
    }

}